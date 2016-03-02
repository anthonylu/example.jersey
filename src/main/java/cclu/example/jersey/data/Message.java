package cclu.example.jersey.data;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class Message {
    private int id;
    @NotNull
    @Length(max=10)
    private String message;
    private boolean isDeleted;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted() {
        this.isDeleted = true;
    }
}
