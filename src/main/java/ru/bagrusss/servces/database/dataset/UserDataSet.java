package ru.bagrusss.servces.database.dataset;

/**
 * Created by vladislav
 */

@SuppressWarnings("ALL")
public class UserDataSet {

    private String password;
    private String email;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    private String firstname;
    private String lastname;
    private long id;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

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

        UserDataSet that = (UserDataSet) o;

        if (!getEmail().equals(that.getEmail())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
