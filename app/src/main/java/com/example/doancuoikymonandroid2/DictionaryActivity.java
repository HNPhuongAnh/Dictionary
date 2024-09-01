package com.example.doancuoikymonandroid2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DictionaryActivity extends AppCompatActivity implements DictionaryAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private DictionaryAdapter adapter;
    private SearchView searchView;
    private Toolbar toolbar;
    private DictionaryAPI dictionaryAPI;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        //Thiết lập searchview
        searchView = findViewById(R.id.SearchViewDictionary);
        setupSearchView();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dictionaryAPI = retrofit.create(DictionaryAPI.class);

        // Thiết lập toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vocabulary");
        setSupportActionBar(toolbar);

        // Thiết lập màu chữ cho title của toolbar
        int whiteColor = ContextCompat.getColor(this, R.color.white); // Thay R.color.white bằng mã màu trắng của bạn
        toolbar.setTitleTextColor(whiteColor);

        // Hiển thị nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        //danh sách từ điển (dictionaryList) từ API
        List<String> dictionaryList = Arrays.asList("Apple", "Animal", "Adventure","Amazing",
                "Active","Architecture","Accurate","Atmosphere","Artistic","Access","Beautiful","Bicycle","Balance","Book","Blue","Brave","Business",
                "Breakfast","Bridge","Brilliant","Challenge", "Creative", "Capture", "Celebration", "Culture", "Comfort", "Confidence", "Chocolate", "Crystal", "Conversation",
                "Delight", "Daring", "Dream", "Discover", "Drama", "Dynamic", "Dazzling", "Define", "Decipher", "Dance",
                "Energy", "Explore", "Embrace", "Enthusiastic", "Excite", "Elegant", "Effortless", "Efficient", "Eager", "Evolve",
                "Freedom", "Flourish", "Fantastic", "Focus", "Flawless", "Friendship", "Fun", "Future", "Fresh", "Fashion",
                "Growth", "Graceful", "Generous", "Gratitude", "Glorious", "Goal", "Gourmet", "Glow", "Giggle", "Genuine",
                "Harmony", "Happiness", "Hope", "Heart", "Honor", "Health", "Humor", "Harmony", "Horizon", "Hug",
                "Inspire", "Imagine", "Innovate", "Intelligent", "Integrity", "Illuminate", "Improve", "Insight", "Ice Cream", "Infinity",
                "Joyful", "Journey", "Jovial", "Jubilant", "Jigsaw", "Jazzy", "Jump", "Jewel", "Jolly", "Juicy",
                "Kindness", "Knowledge", "Keen", "Kaleidoscope", "Kiss", "King", "Kite", "Knock", "Kitty", "Keyboard",
                "Love", "Laugh", "Learn", "Luminous", "Liberty", "Legacy", "Leisure", "Lively", "Luxury", "Light",
                "Motivate", "Magic", "Marvelous", "Music", "Mindful", "Moment", "Magnify", "Master", "Mingle", "Miracle",
                "Nature", "Nurture", "Navigate", "Noble", "Nourish", "Nocturnal", "Novel", "Network", "Nifty", "Neon",
                "Optimize", "Open", "Outstanding", "Overcome", "Oasis", "Ocean", "Opportunity", "Original", "Organize", "Optimistic",
                "Passion", "Peace", "Persevere", "Play", "Prosper", "Positive", "Progress", "Purpose", "Purity", "Power",
                "Quench", "Quirky", "Quality", "Quantum", "Quest", "Quick", "Quiet", "Quote", "Quilt", "Queen",
                "Radiant", "Refresh", "Relish", "Rise", "Radiate", "Rejoice", "Robust", "Rainbow", "Reflect", "Resilient",
                "Serene", "Smile", "Savor", "Sparkle", "Success", "Symphony", "Sincere", "Serendipity", "Splendid", "Spirit",
                "Tranquil", "Triumph", "Trust", "Timeless", "Thrive", "Treasure", "Triumph", "Transform", "Tranquility", "Tenacity",
                "Unleash", "Unity", "Uplift", "Unique", "Unwind", "Upgrade", "Ultimate", "Understand", "Universe", "Unforgettable",
                "Vision", "Victory", "Vibrant", "Venture", "Valor", "Vitality", "Variety", "Validate", "Virtue", "Voyage",
                "Wonder", "Wisdom", "Wholesome", "Win", "Welcome", "Wondrous", "Wander", "Wealth", "Warmth", "Witty",
                "Xenial", "X-factor", "Xanadu", "Xylophone", "Xenon", "Xerox", "Xmas", "Xerophyte", "Xylograph", "Xenophobe",
                "Yes", "Yummy", "Yield", "Yearn", "Yippee", "Yarn", "Yearning", "Yodel", "Yoga", "Youthful",
                "Zen", "Zest", "Zeal", "Zing", "Zephyr", "Zestful", "Zigzag", "Zestify", "Zillion", "Zip");

        // Tạo và thiết lập Adapter
        adapter = new DictionaryAdapter(dictionaryList, this);
        recyclerView.setAdapter(adapter);
        // Thiết lập quản lý layout cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Xử lý khi nút "Quay lại" được nhấn
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // chuyển đến activity trước đó
        super.onBackPressed();
    }

    @Override
    public void onItemClick(String word) {
        // Xử lý khi một từ được chọn, ví dụ, chuyển đến trang ResultActivity2
        Intent intent = new Intent(DictionaryActivity.this, ResultActivity2.class);
        intent.putExtra("WORD", word);
        startActivity(intent);
    }


    //Sử lý Searchview
    private void setupSearchView() {
        // Thiết lập sự kiện cho SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi nhấn nút "Search" trên bàn phím ảo của SearchView
                getWordDetails(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi người dùng thay đổi văn bản trong SearchView
                return false;
            }
        });

    }

    private void getWordDetails(String word) {
        Call<List<DictionaryEntry>> call = dictionaryAPI.getWordDetails(word);
        call.enqueue(new Callback<List<DictionaryEntry>>() {
            @Override
            public void onResponse(Call<List<DictionaryEntry>> call, Response<List<DictionaryEntry>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    DictionaryEntry entry = response.body().get(0);

                    if (entry != null) {
                        List<DictionaryEntry.Phonetic> phonetics = entry.getPhonetics();

                        if (phonetics != null && !phonetics.isEmpty()) {
                            String audioPath = phonetics.get(0).getAudio();

                            if (audioPath != null) {
                                // Tạo Intent và chuyển hướng sang ResultActivity
                                Intent intent = new Intent(DictionaryActivity.this, ResultActivity.class);
                                intent.putExtra("WORD_DETAILS", formatWordDetails(entry));
                                intent.putExtra("AUDIO_PATH", audioPath);
                                startActivity(intent);
                            } else {
                                Log.e("API Error", "Audio path is null");
                            }
                        } else {
                            Log.e("API Error", "Phonetics list is null or empty");
                        }
                    } else {
                        Log.e("API Error", "Response body is null or empty");
                    }
                } else {
                    Log.e("API Error", "Error: " + response.message());
                    // Xử lý khi không tìm thấy từ
                    // Ví dụ: hiển thị Toast thông báo
                    Toast.makeText(DictionaryActivity.this, "Không tìm thấy từ", Toast.LENGTH_SHORT).show();
                }
            }

            //Lỗi
            @Override
            public void onFailure(Call<List<DictionaryEntry>> call, Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                // Xử lý khi có lỗi kết nối
                // Ví dụ: hiển thị Toast thông báo
                Toast.makeText(DictionaryActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatWordDetails(DictionaryEntry entry) {
        StringBuilder details = new StringBuilder();
        details.append("<b>Word:</b> ").append(entry.getWord()).append("<br>");
        details.append("<b>Phonetic:</b> ").append(entry.getPhonetic()).append("<br>");
        details.append("<b>Origin:</b> ").append(entry.getOrigin()).append("<br>");

        details.append("<br><b>Phonetics:</b><br>");
        for (DictionaryEntry.Phonetic phonetic : entry.getPhonetics()) {
            details.append("- <b>Text:</b> ").append(phonetic.getText()).append("<br>");
        }

        details.append("<br><b>Meanings:</b><br>");
        for (DictionaryEntry.Meaning meaning : entry.getMeanings()) {
            details.append("- <b>Part of Speech:</b> ").append(meaning.getPartOfSpeech()).append("<br>");
            details.append("  <b>Definitions:</b><br>");
            for (DictionaryEntry.Meaning.Definition definition : meaning.getDefinitions()) {
                details.append("  - <b>Definition:</b> ").append(definition.getDefinition()).append("<br>");
                details.append("    <b>Example:</b> ").append(definition.getExample()).append("<br>");
            }
        }

        // Sử dụng Html.fromHtml để hiển thị văn bản được định dạng trong TextView
        return details.toString();
    }


}





