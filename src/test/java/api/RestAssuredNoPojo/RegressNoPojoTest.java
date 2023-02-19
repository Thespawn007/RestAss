package api.RestAssuredNoPojo;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RegressNoPojoTest {
    private final static String url = "https://reqres.in";

    @Test
    public void checkAvatarAndIdTestNoPojo() {
        Specifications.installSpecification(Specifications.requestSpec(url), Specifications.responseSpecOK200());
        Response response = given()
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .body("page", equalTo(2))
                .body("data.id", notNullValue())
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", notNullValue())
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.get("data.email");
        List<Integer> ids = jsonPath.get("data.id");
        List<String> avatars = jsonPath.get("data.avatar");
        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }
        Assert.assertTrue(emails.stream().allMatch(x -> x.endsWith("@reqres.in")));
    }

    @Test
    public void successUserRegNoPojo() {
        Specifications.installSpecification(Specifications.requestSpec(url), Specifications.responseSpecOK200());
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");
        Response response = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String token = jsonPath.get("token");
        Assert.assertEquals(4, id);
        Assert.assertEquals("QpwL5tke4Pnpja7X4", token);

    }

    @Test
    public void unSuccessRegTest() {
        Specifications.installSpecification(Specifications.requestSpec(url), Specifications.responseSpecError400());
        Map<String, String> user = new HashMap<>();
        user.put("email", "sudney@fife");
        Response response = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .body("error", equalTo("Missing password"))
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        String error = jsonPath.get("error");
        Assert.assertEquals("Missing password", error);
    }
}
//        UnsuccessReg unSuccessReg = given()
//                .body(user)
//                .post("/api/register")
//                .then().log().all()
//                .extract().as(UnsuccessReg.class);
//        Assert.assertEquals("Missing password", unSuccessReg.getError());
//    }
//    @Test
//    public void sortedYearsTest(){
//        Specifications.installSpecification(Specifications.requestSpec(url),Specifications.responseSpecOK200());
//        List<ColorsData> colors = given()
//                .when()
//                .get("/api/unknown")
//                .then().log().all()
//                .extract().body().jsonPath().getList("data",ColorsData.class);
//        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
//        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
//        Assert.assertEquals(sortedYears,years);
//        System.out.println(years);
//        System.out.println(sortedYears);
//    }
//    @Test
//    public void deleteUser(){
//        Specifications.installSpecification(Specifications.requestSpec(url),Specifications.responseSpecUnikue(204));
//        given()
//                .when()
//                .delete("/api/users/2")
//                .then().log().all();
//
//    }
//    @Test
//    public void timeTest(){
//        Specifications.installSpecification(Specifications.requestSpec(url),Specifications.responseSpecOK200());
//        UserTime user = new UserTime("morpheus","zion resident");
//        UserTimeResponse response = given()
//                .body(user)
//                .when()
//                .put("/api/users/2")
//                .then().log().all()
//                .extract().as(UserTimeResponse.class);
//        String regex = "[^.]*.$";
//        String regex2 = "(.{2})$";
//        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex,"").replaceAll(regex2,"");
//        System.out.println(currentTime);
//        Assert.assertEquals(currentTime,response.getUpdatedAt().replaceAll(regex,"").replaceAll(regex2,""));
//        System.out.println(response.getUpdatedAt().replaceAll(regex,"").replaceAll(regex2,""));
//
//
//    }


