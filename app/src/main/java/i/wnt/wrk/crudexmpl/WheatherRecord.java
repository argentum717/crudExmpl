package i.wnt.wrk.crudexmpl;

/**
 * Created by argentum717 on 23.12.2015.
 * Данный класс представляет из себя объектное отражение
 * записи в таблице БД. Никаких вычислительных операция
 * или обработки данных в нем нет, только хранение и
 * предоставлени доступа к данным.
 */

public class WheatherRecord {
    private int id; // identify
    private String city;
    private int tmp; // temperature, Celseus ))

    public WheatherRecord(int id, String city, int tmp) {
        this.id = id;
        this.city = city;
        this.tmp = tmp;
    }

    public WheatherRecord(String city, int tmp) {
        this.city = city;
        this.tmp = tmp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTmp() {
        return tmp;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }
}
