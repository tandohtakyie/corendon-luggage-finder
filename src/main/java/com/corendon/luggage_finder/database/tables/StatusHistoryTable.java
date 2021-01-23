/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corendon.luggage_finder.database.tables;

import com.corendon.luggage_finder.implementables.DatabaseTable;
import com.corendon.luggage_finder.model.Status;
import com.corendon.luggage_finder.model.StatusHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the 'StatusesHistory' table inside the database and
 * contains all associated queries.
 *
 * @author Dylan Tweebeeke
 */
public class StatusHistoryTable extends DatabaseTable<StatusHistory> {

    /**
     * gets all the items from the StatusesHistory table
     *
     * @param size
     * @return
     */
    @Override
    public List<StatusHistory> getAll(int size) {
        return null;
    }

    /**
     * gets the history from an luggage item by the id
     *
     * @param id
     * @return
     */
    public List<StatusHistory> getHistoryByLuggageId(int id) {
        String query = "SELECT  statuses_history.created_timestamp, luggage_id,status_id, statuses.name FROM corendon.statuses_history \n"
                + "INNER JOIN statuses ON statuses.id = status_id\n"
                + "WHERE luggage_id = ? ORDER BY created_timestamp;";

        List<StatusHistory> history = new ArrayList<>();

        try (ResultSet rs = getJdbc().executeSelectQuery(query, id)) {
            while (rs != null && rs.next()) {
                String name = rs.getString("name");
                Timestamp timeStamp = rs.getTimestamp("created_timestamp");
                Status status = new Status(name);

                StatusHistory statusHistory = new StatusHistory(id, status, timeStamp);

                history.add(statusHistory);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return history;
    }

    /**
     * inserts data into the database
     *
     * @param entry
     * @return
     */
    @Override
    public boolean insert(StatusHistory entry) throws SQLException {
        String query = "INSERT INTO statuses_history (luggage_id,status_id) VALUES (?,?);";

        return getJdbc().executeUpdateQuery(query, entry.getLuggageId(), entry.getStatus().getId());
    }

    /**
     * updates an column in the cell
     *
     * @param entry
     * @return
     */
    @Override
    public boolean update(StatusHistory entry) throws SQLException {
        String query = "UPDATE statuses_history SET status_id = ? WHERE luggages_id = ?;";

        return getJdbc().executeUpdateQuery(query, entry.getStatus(), entry.getLuggageId());
    }

    /**
     * deletes a row from the database
     *
     * @param entry
     * @return
     */
    @Override
    public boolean delete(StatusHistory entry) throws SQLException {
        String query = "DELETE FROM statuses_history WHERE luggages_id = ?;";

        return getJdbc().executeUpdateQuery(query, entry.getLuggageId());
    }

}
