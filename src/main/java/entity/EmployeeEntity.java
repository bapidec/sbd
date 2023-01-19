package entity;

import jakarta.persistence.*;

import java.lang.annotation.Annotation;
import java.sql.Timestamp;

@Entity
@Table(name = "employee", schema = "sbd", catalog = "")
@NamedQuery(name = "EmployeeEntity.ids", query = "SELECT e.employeeId FROM EmployeeEntity e")
@NamedQuery(name = "EmployeeEntity.nameById", query = "SELECT e.name FROM EmployeeEntity e WHERE e.id = :employee_id")
@NamedQuery(name = "EmployeeEntity.surnameById", query = "SELECT e.surname FROM EmployeeEntity e WHERE e.id = :employee_id")
public class EmployeeEntity implements Entity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "employee_id")
    private int employeeId;
    @Basic
    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "sex")
    private String sex;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone_nr")
    private String phoneNr;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeEntity that = (EmployeeEntity) o;

        if (employeeId != that.employeeId) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phoneNr != null ? !phoneNr.equals(that.phoneNr) : that.phoneNr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId;
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNr != null ? phoneNr.hashCode() : 0);
        return result;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }
}
