package Utils;

import Entities.Column;
import Entities.Entity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Mapper {
    @SuppressWarnings("unchecked")
    public static <T> List<T> map(ResultSet resultSet, Class clazz) {
        List<T> result = new LinkedList<>();
        try {
            if (resultSet != null && clazz.isAnnotationPresent(Entity.class)) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                Field[] fields = clazz.getDeclaredFields();

                while(resultSet.next()) {
                    T entity = (T) clazz.newInstance();
                    for (int index = 0; index < metaData.getColumnCount(); index++) {
                        String columnName = metaData.getColumnName(index+1);
                        Object columnValue = resultSet.getObject(index+1);

                        for(Field field : fields) {
                            if (field.isAnnotationPresent(Column.class)) {
                                Column column = field.getAnnotation(Column.class);
                                if (StringUtils.equalsIgnoreCase(column.name(), columnName) && columnValue != null) {
                                    BeanUtils.setProperty(entity, field.getName(), columnValue);
                                    break;
                                }
                            }
                        }
                    }
                    result.add(entity);
                }
            }
        } catch (Exception e){
            return Collections.emptyList();
        }
        return result;
    }
}
