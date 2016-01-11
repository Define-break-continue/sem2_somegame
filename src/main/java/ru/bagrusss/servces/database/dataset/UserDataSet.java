package ru.bagrusss.servces.database.dataset;

/**
 * Created by vladislav
 */

@SuppressWarnings({"unused", "InstanceVariableNamingConvention"})
public class UserDataSet {

    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private long id;

    public UserDataSet(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public UserDataSet(long id, String email, String password) {
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public UserDataSet(long id, String email) {
        this.email = email;
        this.id = id;
    }

    public UserDataSet(long id, String email, String password, String firstname, String lastname) {
        this.password = password;
        this.email = email;
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataSet cmp = (UserDataSet) o;
        return email.equals(cmp.email);

    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

}
