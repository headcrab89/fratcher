# fratcher

Das Backend ist eine Spring Boot Anwendung mit Maven, die ich über IntelliJ gestartet habe.
Zum Initialisieren des Frontends ``npm install`` im Frontend-Ordnder und
zum starten ``npm start``.

Die Anwendung ist auch auf heroku unter https://fratcherog.herokuapp.com/#/ erreichbar.

## Entscheidungen
### Model:
Es gibt ein **User**-Objekt welches, mit einem **Text** Objekt über eine **author**
Variable verbunden ist. Auf diese Weise kann können die gesamten Nutzertexte leichter an
das Frontend ausgegeben werden. Da an den Stellen an denn die Texte gebraucht werden
die Text-Objekte aufgerufen werden und ansonsten das User-Objekt. Ein Nachteil an dieser
Entscheidung ist aber, das an Stellen an denen Text und User gebraucht werden zwei Anfragen
gesendet werden müssen.

Die Matchs werden mit einem **Match**-Objekt gespeichert. Dabei gibt es eine
**initUser**-Variable, das ist er User der, den Match initiiert hat. Das Feld **matchUser**
bezieht sich auf den User, den es betrifft. Daneben gibt es noch den **matchStatus**, den
ich mit einem Enum realisiert habe. Als Status gibt es LIKE, DISLIKE und BOTH_LIKE.
Während LIKE und DISLIKE bedeuten, das der initUser den matchUser mag bzw. nicht mag.
Bedeutet BOTH_LIKE, das beide USER sich mögen und einen Match haben, d.h.
beide User teilen sich ein Match-Objekt. Immer wenn ein Nutzer, einen Text geliked
hat, wird überprüft, ob der andere User diesen auch schon geliked hat. Falls ja wird das
Match-Objekt aktualisiert und es wird kein neues angelegt. Der Hauptgrund warum
ich das so umgesetzt habe ist, weil ich auf diese Weise die Chatnachrichten welche über ein
**Comment** umgesetzt sind, an ein Matchobjekt hängen kann. Anstatt diese immer an
zwei Match-Objekte hängen zu müssen. Auch ist die Datenbank dadurch lesbarer, da man
sofort sieht, wer wenn mag und ob diese ein Match haben. Dadurch müssen auch weniger
Match-Objekte angelegt werden. Ein Nachteil ist aber das dadurch die Queries und
Überprüfungen in den Controllern komplizierter werden, da nicht nur geprüft werden muss
ob der betreffende User der initUser ist, sondern auch im Fall von BOTH_LIKE auch
ob dieser der matchUser ist. Zudem muss im Fronten oft überprüft werden ob der User
der initUnser oder der matchUser ist, beispielsweise um den Namen des Chatpartners
zu bekommen.

LIKE und DISLIKE Matches können wieder gelöscht werden, aber BOTH_LIKE Matches nicht mehr.
Da an BOTH_LIKE Matches auch Kommentare hängen, wäre das schwierig umzusetzen gewesen. Ich
hätte es so machen können das dann beide User ihre Zustimmung hätten geben müssen
oder ich hätte das Objekt dann wieder auf ein LIKE umwandeln können. Letztendlich habe ich es
aber Verboten, da ich es als Umsetzung nicht sehr interessant fand und es ja keine
Voraussetzung war. Sobald LIKE und DISLIKE Matches gelöscht sind, können diese beim
Suchen der Texte wieder gefunden werden.

Zu den Kommentaren habe ich entschieden das diese nicht mehr gelöscht bzw. verändert werden
können. Ursprünglich hatte ich die Funktionalität bereits im Backend umgesetzt, aber mich dann
dagegen entschieden, weil ich es so umsetzen wollte wie bei anderen Messengerdiensten
wie z.B. WhatsApp. Dort ist es auch nicht möglich, sobald die Nachrichten abgeschickt sind.

### Frontend
Für die Anzeige von Uhrzeit und Datum habe ich moment.js verwendet. Mit moment.js können
Uhrzeiten leichter in lesbare Formate umgewandelt werden und mit diesen gerechnet werden.
Zudem ist das Javascript ``Date.toString()`` in Englisch, da ich aber eine Deutsche Übersetzung
habe, hätte ich den String kürzen müssen. Was keine schöne Lösung wäre.
Der Nachteil an moment.js ist, das es relativ groß ist, dafür aber sehr viel
Funktionalität hat.

Bei der Registrierung gibt es keine Bestätigung durch eine Email. Das habe ich aus Zeitgründen
weggelassen. Es wird aber geprüft, ob der Username bereits vergeben ist und das Passwort muss
wiederholt werden.

Um sich Texte ansehen zu können, muss man eingeloggt sein. Deshalb kann man sich auf der
Starseite entweder nur einloggen oder registrieren. Da bei der match/find Seite nur Texte angezeigt
werden, die der Nutzer nicht kennt. Bei der match/list Seite werden alle Matches angezeigt,
die ein User hat.

## Zusatzfeatures
In den Einstellungen kann der Text wieder geändert werden.

Nutzer sehen beim Chatten, ob ihr Gesprächspartner gerade online ist oder wann diese
zuletzt online waren. Das habe ich so umgesetzt, das bei jeder Aktion die ein Nutzer macht eine
**lastActivity** Variable vom Typ Date hochgesetzt wird. Wenn nach 5 Minuten keine Aktion
ausgeführt wurde, wird der Nutzer als nicht online angezeigt und seine letzte Aktivitätszeit
angezeigt.

Der Chat enthält eine Websocket Kommunikation .d.h. wenn ein Chatpartner eine Nachricht
schreibt, wird die Nachricht sofort bei dem Nutzer angezeigt, anstatt erst nach einem Refresh.
Bei der Lösung habe ich mich an das Beispiel aus der Vorlesung gehalten. Ich denke aber,
das es besser gewesen wäre eine Bibliothek zu verwenden. Da man hier nicht mit JSON arbeiten
konnte. Die Nachrichten enthalten keine Chatnachrichten sondern, nur ein String mit der
Chatpartner-ID. Das habe ich gemacht, damit nur dieser die Nachricht erhalten kann.
Wenn eine Websocket-Nachricht angekommen ist, wird bei Frontend ein Befehl zur
Aktualisierung aufgerufen. Damit wird nicht nur die persistierte Nachricht angezeigt,
sondern auch der Onlinestatus des Chatpartners aktualisiert.

