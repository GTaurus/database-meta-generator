package io.gtaurus.db.utils;

import io.gtaurus.db.meta.models.DbConnModel;
import io.gtaurus.db.meta.models.TableMetaSummaryModel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by GTaurus on 2019/2/11.
 */
public class MetaUtils {

    public static List<TableMetaSummaryModel> genMeta(
            String clazz,
            String url,
            String user,
            String password,
            boolean remarksReporting,
            String schema,
            String tablePattern) {
        try (Connection conn =
                     DBUtils.getConnection(genDbConnModel(clazz, url, user, password, remarksReporting))) {
            DatabaseMetaData metaData = conn.getMetaData();

            return AssembleUtils.assembleSummary(metaData, schema, tablePattern);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static DbConnModel genDbConnModel(
            String clazz, String url, String user, String password, boolean remarksReporting) {
        DbConnModel connModel = new DbConnModel();
        connModel.setClazz(clazz);
        connModel.setUrl(url);
        connModel.setUser(user);
        connModel.setPassword(password);
        connModel.setRemarksReporting(remarksReporting);
        return connModel;
    }
}
