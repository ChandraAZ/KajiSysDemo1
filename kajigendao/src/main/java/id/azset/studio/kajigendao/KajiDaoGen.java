package id.azset.studio.kajigendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;
import org.greenrobot.greendao.generator.ToMany;
import org.omg.PortableInterceptor.USER_EXCEPTION;

/**
 * Created by id_admchaset on 10/17/2017.
 */

public class KajiDaoGen {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1,"id.azset.studio.data.dao");
        AddTables(schema);
        try {
            new DaoGenerator().generateAll(schema, "../data/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void AddTables(Schema schema){
        /* entities */
        Entity Kaji = AddKaji(schema);
        Entity ManageKaji = AddManageKaji(schema);
        Entity Category = AddCategory(schema);
        Entity Ustadz = AddUstadz(schema);


// add the foreign key "pictureId" to the "user" entity
       // Property pictureIdProperty = Kaji.addLongProperty("UstadzId").getProperty();
// set up a to-one relation to the "picture" entity
        //

        Property ustadzId = Kaji.addLongProperty("UstadzId").getProperty();
        Property categoryId = Kaji.addLongProperty("CategoryId").getProperty();
        Kaji.addToOne(Ustadz, ustadzId);
        Kaji.addToOne(Category, categoryId);
        //Property categoryId = Kaji.addLongProperty("categoryId").notNull().getProperty();
        //ToMany categoryTokajis = Category.addToMany(Kaji, categoryId);
        //categoryTokajis.setName("Categories");

        // set up a to-one relation to the "picture" entity
       // Kaji.addToOne(Ustadz, ustadzId);

        //customerToOrders.setName("orders"); // Optional
        //customerToOrders.orderAsc(orderDate); // Optional
        /* properties */
        //Property UstadzId = Kaji.addLongProperty("UstadzId").notNull().getProperty();
        //Ustadz.addToOne(Kaji,UstadzId);
        //Ustadz.addToMany(Kaji, UstadzId, "Ustadz");

        //Property categoryId = Kaji.addLongProperty("CategoryId").notNull().getProperty();
        //Category.addToOne(Kaji,categoryId);
        //Category.addToMany(Kaji, categoryId, "Category");

    }

    private static Entity AddKaji(Schema schema){
        Entity Kaji = schema.addEntity("DBKaji");
        Kaji.addIdProperty().primaryKey().autoincrement();
        //Kaji.addLongProperty("UstadzId").notNull();
        //Kaji.addLongProperty("CategoryId").notNull();
        Kaji.addStringProperty("Title").notNull();
        Kaji.addStringProperty("Pemateri").notNull();
        Kaji.addStringProperty("Duration").notNull();
        Kaji.addStringProperty("Size").notNull();
        Kaji.addStringProperty("Description");
        Kaji.addStringProperty("Url1").notNull();
        Kaji.addStringProperty("Url2");
        Kaji.addStringProperty("FileName");
        Kaji.addStringProperty("TypeFile");
        Kaji.addStringProperty("Preview");
        Kaji.addIntProperty("Quality");
        Kaji.addStringProperty("Location");
        Kaji.addDateProperty("Date");
        Kaji.addDateProperty("CreatedDate").notNull();
        return Kaji;
    }

    private static Entity AddManageKaji(Schema schema) {
        Entity ManageKaji = schema.addEntity("DBManageKaji");
        ManageKaji.addIdProperty().primaryKey().autoincrement();
        ManageKaji.addBooleanProperty("IsFavorite").notNull();
        ManageKaji.addBooleanProperty("IsDownload").notNull();
        ManageKaji.addBooleanProperty("IsNew").notNull();
        ManageKaji.addStringProperty("LocalPath1");
        ManageKaji.addStringProperty("LocalPath2");
        ManageKaji.addIntProperty("Rating");
        ManageKaji.addBooleanProperty("IsDeleted").notNull();
        return ManageKaji;
    }

    private static Entity AddCategory(Schema schema){
        Entity Category = schema.addEntity("DBCategory");
        Category.addLongProperty("CategoryId").primaryKey();
        //Category.addIdProperty().primaryKey();
        Category.addStringProperty("Name").notNull();
        Category.addBooleanProperty("IsDeleted");
        return Category;
    }

    private static Entity AddUstadz(Schema schema) {
        Entity Ustadz = schema.addEntity("DBUstadz");
        Ustadz.addLongProperty("UstadzId").primaryKey();
        //Ustadz.addIdProperty().primaryKey();
        Ustadz.addStringProperty("Name").notNull();
        Ustadz.addStringProperty("Study");
        Ustadz.addStringProperty("Description");
        return Ustadz;
    }

}
