package com.dianping.puma.model;

import java.util.*;

public class TableSet {

	List<Table> tables = new ArrayList<Table>();

	public TableSet() {
	}

	public List<Table> listSchemaTables() {
		return tables;
	}

	public Map<String, List<String>> mapSchemaTables() {
		Map<String, List<String>> schemaTableMap = new HashMap<String, List<String>>();
		for (Table table : listSchemaTables()) {
			List<String> tables = schemaTableMap.get(table.getSchemaName());
			if (tables == null) {
				tables = new ArrayList<String>();
			}
			tables.add(table.getTableName());
			schemaTableMap.put(table.getSchemaName(), tables);
		}
		return schemaTableMap;
	}

	public void add(Table table) {
		for (Iterator<Table> it = tables.iterator(); it.hasNext();) {
			Table oriTable = it.next();
			if (oriTable.contains(table)) {
				return;
			} else if (table.contains(oriTable)) {
				it.remove();
			}
		}
		tables.add(table);
	}

	public void addAll(TableSet tableSet) {
		for (Table table : tableSet.listSchemaTables()) {
			add(table);
		}
	}

	public boolean contains(Table table) {
		for (Table tmpTable : listSchemaTables()) {
			if (tmpTable.contains(table)) {
				return true;
			}
		}
		return false;
	}

	public TableSet getIncrement(TableSet tableSet) {
		TableSet result = new TableSet();
		for (Table newTable : tableSet.listSchemaTables()) {
			boolean contains = false;

			for (Table oldTable : this.listSchemaTables()) {
				if (oldTable.contains(newTable)) {
					contains = true;
					break;
				}
			}

			if (!contains) {
				result.add(newTable);
			}
		}
		return result;
	}

	public TableSet getDecrement(TableSet tableSet) {
		return tableSet.getIncrement(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof TableSet)) {
			return false;
		}
		TableSet tableSet = (TableSet) o;
		if (tableSet.tables.size() != this.tables.size()) {
			return false;
		} else {
			for (Table table : tableSet.tables) {
				if (!this.contains(table)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (Table table : this.tables) {
			result = prime * result + table.hashCode();
		}
		return result;
	}

	@Override
	public String toString() {
		return "TableSet{" + "tables=" + tables + '}';
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}
