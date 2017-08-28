# fratcher

Das Backend ist eine Spring Boot Anwendung mit Maven, die ich über IntelliJ gestartet habe.
Zum Initialisieren des Frontends wird der Befehl ``npm install`` im Frontend-Ordnder und
zum Starten ``npm start`` eingegeben.

Die Anwendung ist auch auf heroku unter https://fratcherog.herokuapp.com/#/ erreichbar.

## Entscheidungen
### Model:
Es gibt ein **User**-Objekt welches, mit einem **Text** Objekt über eine **author**
Variable verbunden ist. Auf diese Weise können die gesamten Nutzertexte leichter an
das Frontend ausgegeben werden. Da an den Stellen an denen nur die Texte gebraucht werden,
die Text-Objekte aufgerufen werden und ansonsten das User-Objekt. Ein Nachteil an dieser
Entscheidung ist aber, dass an Stellen an denen Text und User gebraucht werden zwei Anfragen
gesendet werden müssen.

Die Matchs werden mit einem **Match**-Objekt gespeichert. Dabei gibt es eine
**initUser**-Variable, das ist der User der, den Match initiiert hat. Das Feld **matchUser**
bezieht sich auf den User, den das Match betrifft. Daneben gibt es noch den **matchStatus**,
den ich mit einem Enum realisiert habe. Als Status gibt es LIKE, DISLIKE und BOTH_LIKE.
Während LIKE und DISLIKE bedeuten, dass der initUser den matchUser mag bzw. nicht mag,
bedeutet BOTH_LIKE, dass beide USER sich mögen und einen Match haben. Dies heißt, dass
beide User sich ein Match-Objekt teilen. Immer wenn ein Nutzer einen Text geliked
hat, wird überprüft, ob der andere User diesen auch schon geliked hat. Falls ja, wird das
Match-Objekt aktualisiert und es wird kein neues angelegt. Der Hauptgrund warum
ich dies umgesetzt habe, ist weil ich auf diese Weise die Chatnachrichten, welche über ein
**Comment** umgesetzt sind an ein Matchobjekt hängen kann, anstatt diese immer an
zwei Match-Objekte hängen zu müssen. Auch ist die Datenbank dadurch lesbarer, da man
sofort sieht, wer wenn mag und ob diese User ein Match haben. Dadurch müssen auch weniger
Match-Objekte angelegt werden, was einen Vorteil im Speicherverbrauch darstellt. 
Ein Nachteil ist aber, dass dadurch die Queries und Überprüfungen in den Controllern 
komplizierter werden, da nicht nur geprüft werden muss, ob der betreffende User der 
initUser ist, sondern auch im Fall von BOTH_LIKE ob dieser der matchUser ist. 
Zudem muss im Fronten oft überprüft werden, ob der User der initUnser oder der matchUser ist, 
beispielsweise um den Namen des Chatpartners zu bekommen.

LIKE und DISLIKE Matches können wieder gelöscht werden. Dies gilt aber nicht für BOTH_LIKE 
Matches, da an BOTH_LIKE Matches auch Kommentare hängen, was schwierig umzusetzen gewesen wäre.
Ich hätte es so machen können, dass dann beide User ihre Zustimmung hätten geben müssen
oder ich hätte das Objekt dann wieder auf ein LIKE umwandeln können. Letztendlich habe ich es
aber Verboten, da ich es als Umsetzung nicht sehr interessant fand. Sobald LIKE und DISLIKE 
Matches gelöscht sind, können diese beim Suchen der Texte erneut gefunden werden.

Bei den Kommentaren habe ich entschieden, dass diese nicht mehr gelöscht bzw. verändert werden
können. Ursprünglich hatte ich diese Funktionalität bereits im Backend umgesetzt, aber mich dann
dagegen entschieden, weil ich es so umsetzen wollte wie bei anderen Messengerdiensten
wie z.B. WhatsApp. Dort ist es auch nicht möglich Nachrichten zu löschen, 
sobald die Nachrichten abgeschickt worden sind.

### Frontend
Für die Anzeige von Uhrzeit und Datum habe ich moment.js verwendet. Mit moment.js können
Uhrzeiten leichter in lesbare Formate umgewandelt werden und mit diesen gerechnet werden.
Zudem ist das Javascript ``Date.toString()`` Datum in englischer Sprache und Formatierung, 
da ich aber eine deutsche Übersetzung habe, hätte ich den String kürzen müssen. Was keine 
schöne Lösung wäre. Der Nachteil an moment.js ist, dass es relativ groß ist, 
dafür aber sehr viel Funktionalitäten bietet.

Bei der Registrierung gibt es keine Bestätigung durch eine Email. Das habe ich aus Zeitgründen
weggelassen. Es wird aber geprüft, ob der Username bereits vergeben ist und das Passwort muss
wiederholt werden.

Um sich Texte ansehen zu können, muss man eingeloggt sein. Deshalb kann man sich auf der
Starseite entweder nur einloggen oder registrieren, da bei der match/find Seite nur Texte angezeigt
werden, die der Nutzer nicht kennt. Bei der match/list Seite werden alle Matches angezeigt,
die ein User hat und daher würde es keinen Sinn machen dies für nicht eigeloggt User anzuzeigen. 

## Zusatzfeatures
In den Einstellungen kann der Text wieder geändert werden und gelöscht werden. Wenn ein Text
gelöscht wurde, wird dieser beim match/find nicht mehr angezeigt.

Nutzer sehen beim Chatten, ob ihr Gesprächspartner gerade online ist oder wann diese
zuletzt online gewesen sind. Das habe ich so umgesetzt, dass bei jeder Aktion, die ein Nutzer 
macht, eine **lastActivity** Variable vom Typ Date hochgesetzt wird. Wenn nach 5 Minuten keine 
Aktion ausgeführt wurde, wird der Nutzer als nicht online angezeigt und seine letzte 
Aktivitätszeit angezeigt.

Der Chat enthält eine Websocket Kommunikation, .d.h., wenn ein Chatpartner eine Nachricht
schreibt, wird die Nachricht sofort bei dem Nutzer angezeigt anstatt erst nach einem Refresh.
Bei der Lösung habe ich mich an das Beispiel aus der Vorlesung gehalten. Ich denke aber,
das es besser gewesen wäre, eine Bibliothek zu verwenden, da man hier nicht mit JSON arbeiten
konnte. Die Nachrichten enthalten keine Chatnachrichten, sondern nur einen String mit der
Chatpartner-ID. Das habe ich gemacht, damit nur dieser die Nachricht erhalten kann.
Wenn eine Websocket-Nachricht angekommen ist, wird im Frontend ein Befehl zur
Aktualisierung aufgerufen. Damit wird nicht nur die persistierte Nachricht angezeigt,
sondern auch der Onlinestatus des Chatpartners aktualisiert.