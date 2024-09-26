











Open-sourcing as I am re-writing (based on a better understanding of Compose) an app I wrote a couple of years ago. 
<p><a href="https://play.google.com/store/apps/details?id=com.coroutines.historycat">History Calendar app on Google Play Store</a></p>
<p></p>
Also, creating a follow-along documentation. Mostly for myself, as I realized that I don't remember why I had made certain decisions in the original version of the app. But if it helps someone too, great :)
All in early stages, work in progress.
<p></p>


<b>This Day In History</b> is a fully functional Android app, with a companion Glance Widget, built entirely with Kotlin 2.0 and Jetpack Compose.   The app allows users to discover what happpened on any particular day with summaries of major events, anniversaries, famous births and notable deaths.
<p></p>
<p>
  
</p>

<table style="width:100%">
  <tr>
    <th>English, Dark Theme</th>
    <th>Glance Widget</th> 
    <th>Spanish, Dark Theme</th> 
  </tr>
  <tr>
    <td><img src="https://github.com/dmitrish/Jetpack-Compose-Android-History-Calendar-App/blob/master/ase6.gif"/></td>
    <td><img src="https://github.com/dmitrish/Jetpack-Compose-Android-History-Calendar-App/blob/master/widget1.gif"/></td> 
    <td><img src="https://github.com/dmitrish/Jetpack-Compose-Android-History-Calendar-App/blob/master/spanishDark.gif"/></td>
  
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
<table style="width:100%">
  <tr>
    <th width="480">Widget Preview and Selection</th>
   <th width="480">Widget Preview</th>
    <th width="480">Arabic Dark</th>
  </tr>
  <tr>
    
    <td width="480"><video src="https://github.com/user-attachments/assets/8f81c87d-6add-4a87-b664-68ca4906cdfb"  /></td>
    <td width="480">
        <video src="https://github.com/user-attachments/assets/6999d2ba-9fb6-47ce-a525-6416a6578386"/>
     
    </td>
        <td><video src="https://github.com/user-attachments/assets/2aa5975e-d038-4441-9279-f453ee0b50b1"/></td>
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
  <li>Jetpack Compose Glance</li>
  <li>Jetpack Compose Navigation</li>
  <li>Workmanager</li>
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
  <li><a href="http://coroutines.com/thisdayinhistory/applytheme">About, Languages, Theme Screens - Part 2 and NavDrawer screen - Apply Theme</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/inapplanguages">In-App Languages/Internationalization</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/welcomescreenmock">Welcome Screen with Mock View Model and Transition to Languages Screen</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/welcomescreen">Welcome Screen with real Translation API and real ViewModel</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/savingonboarding">Welcome message based on device language and intergation with Preferences Data Store</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/splash">Splash Screen</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/uitestaboutscreen">UI Tests: About Screen</a></li>
  <li><a href="http://coroutines.com/Thisdayinhistory/uiTestLanguagesScreen">UI Tests: Languages Screen</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/uitestwelcomescreen">UI Tests: Welcome Screen plus Navigation</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/uitestcatlogo">UI Tests: Cat Logo under different themes</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/historyscreenpart1">Main Screen - Part 1</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/historyscreenpart2">Main Screen - Part 2 - Shimmer Animation on Loading</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/detailscreen">Detail Screen</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/navigationbarcolors">Navigation Bar Colors</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/upbutton">AppBar for secondary screens with Up Button</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/appbar">Primary AppBar with a hamburger menu to open Navigation Drawer and calendar icon to open Calendar</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/datepicker">Date Picker</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/bottomsheet">Modal Bottom Sheet to display expanded image</li>
  <li><a href="http://coroutines.com/thisdayinhistory/searchbar">Search Bar</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/bottombarcalendar">Bottom Bar Date Picker</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/historyviewmodel">Real ViewModel with real API calls</li>
  <li><a href="http://coroutines.com/thisdayinhistory/hilt">Add Hilt with KSP</a></li>
  <li><a href="http://coroutines.com/thisdayinhistory/uitesterrorscreen">Ui Tests: Error Screen</li>
  
<li>More to follow...</li>



</ul>
