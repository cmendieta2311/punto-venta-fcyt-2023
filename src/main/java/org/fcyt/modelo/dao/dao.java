package org.fcyt.modelo.dao;

import java.util.List;

public interface dao<T> {
    public void insertar(T t);
    public void actualizar(T t);
    public void eliminar(T t);
    public List<T> listar();
}
