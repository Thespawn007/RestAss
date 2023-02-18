package api.RestAssuredOne;

public class SuccessReg{
    private Integer id;
    private String token;

    public SuccessReg(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public SuccessReg(){

    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}

