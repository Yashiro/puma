package com.dianping.puma.parser.type;

import com.dianping.puma.core.event.ChangedEvent;
import com.dianping.puma.core.event.RowChangedEvent;
import com.dianping.puma.core.util.sql.DMLType;
import com.dianping.puma.parser.AbstractBaseDebug;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/***
 * text type test
 * 
 * @author qi.yin
 *
 */
public class TextTypeDebug extends AbstractBaseDebug {

	private static final String TABLE_NAME = "tb_text";

	@BeforeClass
	public static void doBefore() throws Exception {
		String create_SQL = "CREATE TABLE IF NOT EXISTS `" + SCHEMA_NAME + "`.`" + TABLE_NAME + "` (\n"
		      + "`id` int NOT NULL AUTO_INCREMENT, \n" + "`binary_text` text BINARY DEFAULT NULL, \n"
		      + "`unbinary_text` text DEFAULT NULL, \n" + "`size_text` text(5) DEFAULT NULL, \n"
		      + "`unsize_text` text DEFAULT NULL, \n" + "PRIMARY KEY (`id`)" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		queryRunner.update(create_SQL);
		setFilterTable(TABLE_NAME);
	}

	@AfterClass
	public static void doAfter() throws Exception {
		String drop_SQL = "DROP TABLE IF EXISTS `" + SCHEMA_NAME + "`.`" + TABLE_NAME + "`";
		queryRunner.update(drop_SQL);
	}

	@Test
	public void textTypeInsertTest() throws Exception {
		test(new TestLogic() {

			@Override
			public void doLogic() throws Exception {
				String[][] testData = { { "ccc", "ccc", "ccc", "ccc" }, { "ccccc", "ccccc", "ccccc", "ccccc" },
				      { "cccccc", "cccccc", "cccccc", "cccccc" } };
				for (int i = 0; i < testData.length; i++) {
					String insert_SQL = "INSERT INTO `" + SCHEMA_NAME + "`.`" + TABLE_NAME
					      + "`(binary_text, unbinary_text, size_text, unsize_text)VALUES(?, ?, ?, ?)";
					queryRunner.update(insert_SQL, testData[i][0], testData[i][1], testData[i][2], testData[i][3]);
				}
				List<ChangedEvent> events = getEvents(testData.length, false, true, false);
				Assert.assertEquals(testData.length, events.size());
				for (int i = 0; i < testData.length; i++) {
					Assert.assertTrue(events.get(i) instanceof RowChangedEvent);
					RowChangedEvent rowChangedEvent = (RowChangedEvent) events.get(i);
					Assert.assertEquals(DMLType.INSERT, rowChangedEvent.getDmlType());
					Assert.assertEquals(TABLE_NAME, rowChangedEvent.getTable());
					Assert.assertEquals(SCHEMA_NAME, rowChangedEvent.getDatabase());
					Assert.assertEquals(5, rowChangedEvent.getColumns().size());
					Assert.assertEquals(testData[i][0], new String((byte[]) rowChangedEvent.getColumns().get("binary_text")
					      .getNewValue(), "UTF-8"));
					Assert.assertEquals(null, rowChangedEvent.getColumns().get("binary_text").getOldValue());
					Assert.assertEquals(testData[i][1], new String((byte[]) rowChangedEvent.getColumns()
					      .get("unbinary_text").getNewValue(), "UTF-8"));
					Assert.assertEquals(null, rowChangedEvent.getColumns().get("unbinary_text").getOldValue());
					Assert.assertEquals(testData[i][2], new String((byte[]) rowChangedEvent.getColumns().get("size_text")
					      .getNewValue(), "UTF-8"));
					Assert.assertEquals(null, rowChangedEvent.getColumns().get("size_text").getOldValue());
					Assert.assertEquals(testData[i][3], new String((byte[]) rowChangedEvent.getColumns().get("unsize_text")
					      .getNewValue(), "UTF-8"));
					Assert.assertEquals(null, rowChangedEvent.getColumns().get("unsize_text").getOldValue());
				}
			}

		});
	}

}
