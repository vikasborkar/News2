package com.vikas.android.news2;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.vikas.android.news2.data.Article;
import com.vikas.android.news2.data.DataStore;
import com.vikas.android.news2.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class News2Test {

  @Before
  public void initDataStore(){
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    DataStore.init(appContext);
  }

  @Test
  public void readJsonFileFromAssets() {
    String dataString = DataStore.get().getJsonString();
    assertTrue(dataString!= null && dataString.length()!=0);
  }

  @Test
  public void parseArticles() {
    String testJson = "{\n" +
        "  \"articles\": [\n" +
        "    {\n" +
        "      \"title\": \"News 2\",\n" +
        "      \"publishedAt\": \"2024-01-09T21:41:56Z\",\n" +
        "      \"urlToImage\": \"urlToImage2\",\n" +
        "      \"url\": \"url2\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"title\": \"News 1\",\n" +
        "      \"publishedAt\": \"2024-01-10T22:41:25Z\",\n" +
        "      \"urlToImage\": \"urlToImage1\",\n" +
        "      \"url\": \"url1\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"title\": \"News 3\",\n" +
        "      \"publishedAt\": \"2024-01-03T16:41:52Z\",\n" +
        "      \"urlToImage\": \"urlToImage3\",\n" +
        "      \"url\": \"url3\"\n" +
        "    }\n" +
        "  ]\n" +
        "}";
    List<Article> actual = DataStore.get().parseJsonData(testJson);

    List<Article> expected = new ArrayList<>();
    expected.add(new Article("News 2", utilParseDate("2024-01-09T21:41:56Z"), "urlToImage2", "url2"));
    expected.add(new Article("News 1", utilParseDate("2024-01-10T22:41:25Z"), "urlToImage1", "url1"));
    expected.add(new Article("News 3", utilParseDate("2024-01-03T16:41:52Z"), "urlToImage3", "url3"));

    assertEquals(actual, expected);
  }

  @Test
  public void sortArticlesByDate() {
    List<Article> actual = new ArrayList<>();
    actual.add(new Article("News 1", utilParseDate("2024-01-10T22:41:25Z"), "urlToImage1", "url1"));
    actual.add(new Article("News 2", utilParseDate("2024-01-09T21:41:56Z"), "urlToImage2", "url2"));
    actual.add(new Article("News 3", utilParseDate("2024-01-03T16:41:52Z"), "urlToImage3", "url3"));

    List<Article> expected = new ArrayList<>();
    expected.add(new Article("News 2", utilParseDate("2024-01-09T21:41:56Z"), "urlToImage2", "url2"));
    expected.add(new Article("News 1", utilParseDate("2024-01-10T22:41:25Z"), "urlToImage1", "url1"));
    expected.add(new Article("News 3", utilParseDate("2024-01-03T16:41:52Z"), "urlToImage3", "url3"));
    DataStore.get().sortArticles(expected);

    assertEquals(actual, expected);
  }

  @Rule
  public ActivityScenarioRule<MainActivity> activityScenarioRule =
      new ActivityScenarioRule<>(MainActivity.class);

  @Test
  public void displayHomeScreen(){
    ActivityScenario<MainActivity> scenario = activityScenarioRule.getScenario();
    scenario.moveToState(Lifecycle.State.RESUMED);

    Espresso.onView(withId(R.id.article_recycler_view))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    Espresso.onView(withId(R.id.menu_item_search))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


  }

  private Date utilParseDate(String dateString) {
    Date date = new Date();
    try {
      date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return date;
  }

}