package api.RestAssuredOne;

public class UnsuccessReg {
    private String error;

    public UnsuccessReg(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
    public UnsuccessReg(){

    }
}
