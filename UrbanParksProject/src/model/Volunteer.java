package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is a volunteer.
 * 
 * @author Reid Thompson
 * @author Arsh Singh
 * @version 5.10.2015
 */
public class Volunteer extends User implements Serializable {

    private static final long serialVersionUID = 8L;

    public Volunteer(String theEmail, String theFirstName, String theLastName) {
        super(theFirstName, theLastName, theEmail);
    }

    @Override
    public boolean equals(Object theO) {
        if (!(theO instanceof Volunteer))
            return false;

        Volunteer theOther = (Volunteer) theO;

        return (super.getFirstName().equals(theOther.getFirstName()) 
                && super.getLastName().equals(theOther.getLastName()))
                || super.getEmail().equals(theOther.getEmail());
    }

    @Override
    public String toString() {
        return super.getFirstName() + " " + super.getLastName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getFirstName(), super.getLastName(), super.getEmail());
    }

}