package pl.coderslab.rentaapartment.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String name;
    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] byteImage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getByteImage() {
        return byteImage;
    }

    public void setByteImage(byte[] byteImage) {
        this.byteImage = byteImage;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", byteImage=" + Arrays.toString(byteImage) +
                '}';
    }

}
