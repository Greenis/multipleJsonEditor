import javafx.scene.control.TreeItem;
import org.junit.Test;
import org.me.entity.JsonManager;
import org.me.ui.table.Line;
import org.me.serialize.JsonReader;

public class SerializationTest {
	
	@Test
	public void test_01() throws Exception {
		JsonManager data = new JsonManager();
		TreeItem<Line> root = new TreeItem<>(data.getRoot());
		data.getJSEngine().eval("var dict = {};");
		data.getJSEngine().eval(String.format("dict['%s'] = %s", "en", "{}"));
		JsonReader reader = new JsonReader(root, data, "en", getClass().getResourceAsStream("./SerializationTest.json"));
		reader.read();
//		JsonWriter writer = new JsonWriter(root, "en", null);
//		writer.write();
	}
	
}
