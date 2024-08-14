import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.kakaoimageapi.data.retrofit.Document
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveSelectedItem(key: String, newItem: Document) {
        Log.i("test","saveSelectedItem")
        val itemList = getSelectedItems(key).toMutableList()
        itemList.add(newItem)
        val jsonString = gson.toJson(itemList)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    fun getSelectedItems(key: String): List<Document> {
        val jsonString = sharedPreferences.getString(key, "[]")
        return gson.fromJson(jsonString, object : TypeToken<List<Document>>() {}.type)
    }

    fun removeSelectedItemByUuid(key: String, uuid: String) {
        Log.i("test", "removeSelectedItemByUuid")
        val itemList = getSelectedItems(key).toMutableList()

        // `uuid`에 해당하는 항목을 찾고 제거
        val itemToRemove = itemList.find { it.uuid == uuid }
        if (itemToRemove != null) {
            itemList.remove(itemToRemove)

            // 목록을 JSON 문자열로 변환하여 저장
            val jsonString = gson.toJson(itemList)
            sharedPreferences.edit().putString(key, jsonString).apply()
        }
    }

    fun clearSelectedItem(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
