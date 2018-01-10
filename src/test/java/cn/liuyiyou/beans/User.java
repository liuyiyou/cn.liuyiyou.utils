package cn.liuyiyou.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class User  implements Serializable{

    private Date birthDay;
    private int id;
    private String name;
    private List<String> hobby;

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "User{" +
                "birthDay=" + birthDay +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", hobby=" + hobby +
                ", address=" + address +
                '}';
    }
}
