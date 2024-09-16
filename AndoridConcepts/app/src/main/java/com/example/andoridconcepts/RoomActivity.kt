package com.example.andoridconcepts

import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import org.w3c.dom.Text

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 기본적으로 데이터베이스 작업은 메인 쓰레드에서 할 수 없다
        // 이유는, 데이터베이스 작업을 휴대폰이 하는 동안 사용자가 기다려야하기 때문
        // 해결책은, 쓰레드를 이용하고 async를 이용하면 된다
        val database = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user-database"
        ).allowMainThreadQueries().build()

        findViewById<TextView>(R.id.save).setOnClickListener {
            val userProfile = UserProfile("홍", "길동")
            database.userProfileDao().insert(userProfile)
        }
        findViewById<TextView>(R.id.load).setOnClickListener {
            val userProfiles = database.userProfileDao().getAll()
            userProfiles.forEach{
                Log.d("testt", "${it.id}")
            }
        }
        findViewById<TextView>(R.id.delete).setOnClickListener {
            database.userProfileDao().delete(1)
        }



    }
}

@Database(entities = [UserProfile::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userProfileDao(): UserProfileDao
}


@Dao
interface UserProfileDao {
    // CRUD -> 데이터 베이스 조작
    // Query -> 데이터 베이스 조회
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userProfile: UserProfile)

    @Query("DELETE FROM userprofile WHERE id = :userId")
    fun delete(userId: Int)

    @Query("SELECT * FROM userprofile")
    fun getAll() : List<UserProfile>
}

@Entity
class UserProfile(

    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "first_name")
    val firstName: String,
) {
    constructor(lastName: String, firstName: String): this(0, lastName, firstName)
}

