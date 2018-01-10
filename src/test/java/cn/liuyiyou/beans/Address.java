package cn.liuyiyou.beans;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class Address {

    private String prov;
    private String city;

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "prov='" + prov + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
