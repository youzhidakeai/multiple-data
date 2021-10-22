package com.dc.multiple.mysql.listener;

import com.dc.multiple.kafka.KafkaSend;
import com.dc.multiple.mysql.TemplateHolder;
import com.dc.multiple.mysql.bo.TableTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private final ObjectMapper getObjectMapper;

    private final KafkaSend kafkaSend;

    private final TemplateHolder templateHolder;

    private String dbName;
    private String tableName;

    public AggregationListener(ObjectMapper getObjectMapper, KafkaSend kafkaSend,
                               TemplateHolder templateHolder) {
        this.getObjectMapper = getObjectMapper;
        this.kafkaSend = kafkaSend;
        this.templateHolder = templateHolder;
    }

    @SneakyThrows
    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            log.info("TableMapEventData : {}", getObjectMapper.writeValueAsString(data));
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if (type == EventType.EXT_WRITE_ROWS || type == EventType.EXT_UPDATE_ROWS || type == EventType.EXT_DELETE_ROWS) {

            TableTemplate table = templateHolder.getTable(tableName);

            if (null == table) {
                log.warn("table {} not found", tableName);
                return;
            }

            List<Map<String, String>> afterMapList = new ArrayList<>();

            for (Serializable[] after : getAfterValues(event.getData())) {

                Map<String, String> afterMap = new HashMap<>();
                int colLen = after.length;

                for (int ix = 0; ix < colLen; ++ix) {

                    // 取出当前位置对应的列名
                    String colName = table.getPosMap().get(ix);

                    // 如果没有则说明不关心这个列
                    if (null == colName) {
                        log.debug("ignore position: {}", ix);
                        continue;
                    }
                    String colValue = after[ix].toString();
                    afterMap.put(colName, colValue);
                }
                afterMapList.add(afterMap);
            }
            TempBO tempBO = new TempBO();
            tempBO.setTableName(tableName);
            tempBO.setEventType(type);
            tempBO.setAfterMapList(afterMapList);
            kafkaSend.sender(getObjectMapper.writeValueAsString(tempBO));
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }

    @Data
    public static class TempBO {

        private String tableName;

        private EventType eventType;

        private List<Map<String, String>> afterMapList;
    }
}
