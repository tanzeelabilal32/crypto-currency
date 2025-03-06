# **CryptoCurrencyRates - Android App**

A modern **Android application** built with **Kotlin** that displays the latest cryptocurrency prices. This app fetches **real-time crypto data** using a public API and follows best practices like **MVVM architecture, Coroutines, Flow, Room Database, and Jetpack Compose**.

---

## **🚀 Features**

- ✅ Fetches **real-time cryptocurrency prices** using Retrofit & Moshi
- ✅ Displays **crypto names, prices, market caps, and trends**
- ✅ **Caching mechanism**: Stores API responses in **Room Database** for offline access
- ✅ **Error handling** for network failures & API issues
- ✅ **Pagination support** for listing more cryptocurrencies
- ✅ **Unit tests** for ViewModel, Repository, and UseCases
- ✅ Uses **Jetpack Components (ViewModel, LiveData, Flow)**
- ✅ **MVVM Architecture** for clean and scalable codebase

---

## **📦 Tech Stack**

| Category         | Libraries & Tools             |
| ---------------- | ----------------------------- |
| **Language**     | Kotlin                        |
| **Architecture** | MVVM                          |
| **UI**           | Jetpack Compose / XML Layouts |
| **Network**      | Retrofit + Moshi              |
| **Storage**      | Room Database (for caching)   |
| **Concurrency**  | Coroutines + Flow             |
| **Testing**      | JUnit, Mockito, Turbine       |

---

## **🔧 Setup & Installation**

### **1️⃣ Prerequisites**

- **Android Studio** (latest version recommended)
- **Min SDK**: 21 (Android 5.0 Lollipop)
- **Gradle Version**: 8.0+

### **2️⃣ Clone the Repository**

```bash
git clone https://github.com/your-repository-name.git
cd CryptoCurrencyRates
```

### **3️⃣ Open in Android Studio**

- Open **Android Studio**
- Select **"Open an Existing Project"**
- Navigate to the cloned folder & open it

### **4️⃣ API Key Setup**

- Register on **CoinGecko / CoinMarketCap** for API access.
- Add your **API key** inside `local.properties`:
  ```
  API_KEY="your_api_key_here"
  ```
- Modify the API base URL inside `ApiService.kt`:
  ```kotlin
  const val BASE_URL = "https://api.coingecko.com/api/v3/"
  ```

### **5️⃣ Build & Run**

- Click **Run ▶️** in Android Studio.
- Install & run on a **physical device** or **Android emulator**.

---

## **📌 Project Structure**

```
📦 CryptoCurrencyRates
 ┣ 📂 data
 ┃ ┣ 📂 api  (Retrofit API calls)
 ┃ ┣ 📂 database  (Room DB for caching)
 ┃ ┣ 📂 repository  (Manages API & local data)
 ┣ 📂 domain
 ┃ ┣ 📂 model  (Data classes for Crypto)
 ┃ ┣ 📂 usecase  (Business logic for fetching data)
 ┣ 📂 presentation
 ┃ ┣ 📂 viewmodel  (Manages UI state)
 ┃ ┣ 📂 ui  (XML layouts / Jetpack Compose screens)
 ┣ 📂 utils  (Helper classes & error handling)
 ┣ 📜 build.gradle  (Dependencies)
 ┣ 📜 AndroidManifest.xml  (App permissions)
```

---

## **✅ Unit Testing**

Unit tests are written using:

- **JUnit** for core logic
- **Mockito** for mocking dependencies
- **Turbine** for Flow state testing

### **Run Tests**

```bash
./gradlew test
```

---

## **❌ Error Handling**

| Error Type     | Message Displayed                           |
| -------------- | ------------------------------------------- |
| No Internet    | "No connection. Please check your network." |
| API Failure    | "Failed to fetch data. Try again later."    |
| Empty Response | "No data available."                        |

---

## **🔮 Future Enhancements**

- 📊 **Add Graphs for Crypto Trends** (MPAndroidChart)
- 🛑 **Dark Mode Support**
- 📱 **More Coins & Sorting Features**
- 🌍 **Multi-language Support**

---

## **📜 License**

This project is **open-source** under the **MIT License**.

---

### **🚀 Start Tracking Crypto Prices Now! 💰📊**

Feel free to contribute & improve this project! 😊

