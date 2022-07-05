package api;

import api.pojo.ListUsers;
import api.pojo.RegisterSuccessful;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static api.Data.*;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class SomeTests {
    @Test(description = "Check successful registration with using the POST method")
    public void firstTest() {
        User user = new User.UserBuilder().withEmail(EMAIL).withPassword(PASSWORD).build();
        RegisterSuccessful response = given()
                .with()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(REGISTER_URL)
                .then()
                .log().everything()
                .statusCode(HTTP_OK)
                .extract().as(RegisterSuccessful.class);
        Assert.assertEquals(response.getId(), ID);
        Assert.assertNotNull(response.getId());
        Assert.assertNotNull(response.getToken());
    }

    @Test(description = "Check the answer for the content of some fields")
    public void secondTest() {
        List <ListUsers> user = given()
                .when()
                .get(LIST_USER_URL)
                .then()
                .log().everything()
                .statusCode(HTTP_OK)
                .extract()
                .body()
                .jsonPath()
                .getList("data", ListUsers.class);
        for (ListUsers x : user) {
            Assert.assertTrue(x.getAvatar().contains(x.getId().toString()));
        }

        Assert.assertTrue(user.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = user.stream().map(ListUsers::getAvatar).collect(Collectors.toList());
        List<String> ids = user.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }
}
