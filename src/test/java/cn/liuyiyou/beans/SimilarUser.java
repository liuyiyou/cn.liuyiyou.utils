package cn.liuyiyou.beans;

import java.io.Serializable;
import java.util.List;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class SimilarUser implements Serializable{

    private int id;
    private String name;
    private List<String> hobby;
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
}
