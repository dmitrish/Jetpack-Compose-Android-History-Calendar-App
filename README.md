Open-sourcing as I am re-writing (based on a better understanding of Compose) an app I wrote a couple of years ago. 
<p><a href="https://play.google.com/store/apps/details?id=com.coroutines.historycat">History Calendar app on Google Play Store</a></p>
<p></p>
Also, creating a follow-along documentation. Mostly for myself, as I realized that I don't remember why I had made certain decisions in the original version of the app. But if it helps someone too, great :)
All in early stages, work in progress.
<p></p>
<ul>
<li><a href="http://coroutines.com/thisdayinhistory/introduction">Building this app - Introduction</a></li>
<li><a href="http://coroutines.com/thisdayinhistory/toml">Set up Version Catalog</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/setupNavigation">Set up Navigation</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/setupNavigationdrawer">Set up Navigation Drawer</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/logo">App Logo Composable</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/aboutScreen">About Screen - Part 1</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/languagesscreen">Languages Screen - Part 1</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/themescreen">Theme Screen - Part 1</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/applytheme">Apply Theme to About, Languages, Theme, and NavDrawer screens</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/inapplanguages">In-App Languages/Internationalization</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/welcomescreenmock">Welcome Screen with Mock View Model</a><li>
<li>More to follow...</li>

</ul>

<b>This Day In History</b> is a fully functional Android app built entirely with Kotlin 2.0 and Jetpack Compose.   The app allows users to discover what happpened on any particular day with summaries of major events, anniversaries, famous births and notable deaths.
<p></p>
<p>
  
</p>

<table style="width:100%">
  <tr>
    <th>English, Dark Theme</th>
    <th>Portuguese, Dark Theme</th> 
    <th>Arabic, Light Theme</th> 
  </tr>
  <tr>
    <td><img src="https://github.com/dmitrish/This-Day-In-History/blob/develop/app.gif"/></td>
    <td><img src="https://github.com/dmitrish/This-Day-In-History/blob/master/app3.gif"/></td> 
  <td><img src="https://github.com/dmitrish/This-Day-In-History/blob/master/app4.gif"/></td> 
  </tr>
  
</table>
<p></p>
<table style="width:100%">
  <tr>
    <th>Spanish, Detail</th>
    <th>French, Detail</th> 
    <th>Spanish, Settings</th> 
  </tr>
  <tr>
    <td><img src="https://github.com/dmitrish/This-Day-In-History/blob/master/spanishDante.jpg"/></td>
    <td><img src="https://github.com/dmitrish/This-Day-In-History/blob/master/frenchDetail.jpg"/></td> 
  <td><img src="https://github.com/dmitrish/This-Day-In-History/blob/master/spanishSettings.jpg"/></td> 
  </tr>
  
</table>
<p></p>
<p>
   <h2>Technology Stack</h2>
<p></p>
<ul class="list-disc">
  <li>Kotlin 2.0</li>
  <li>Coroutines and Flows</li>
  <li>Jetpack Compose</li>
  <li>Jetpack Compose Navigation</li>
  <li>Hilt</li>
  <li>Retrofit2</li>
  <li>Material3</li>
  <li>Spotless</li>
  <li>Detekt</li>
</ul>
</p>
<p></p>
<p></p>
The app supports internationalization and in-app language selection. The content will be available in the following languages:
<p></p>
 <ul>
            <li>Arabic</li>
            <li>English</li>
            <li>French</li>
            <li>Italian</li>
            <li>German</li>
            <li>Spanish</li>
            <li>Swedish</li>
            <li>Russian</li>
            <li>Portuguese</li>
   
</ul>
