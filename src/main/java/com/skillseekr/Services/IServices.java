package com.skillseekr.Services;

import com.skillseekr.Services.Claims.ServiceReponse;

import java.sql.SQLException;
import java.util.List;

public interface IServices<T>{
    void add(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(T t) throws SQLException;
    List<T> show() throws SQLException;
    void addReclamation(T t, int userId) throws SQLException;

}
