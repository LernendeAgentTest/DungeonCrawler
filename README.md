# DungeonCrawler
"Dungeon Koala" ist ein Spiel das wir uns im Rahmen des Projekts "Lernende Agenten" ausgedacht haben, um einen Agenten daran lernen zu lassen. Es ist ein rundenbasiertes Spiel, bei dem ein Spieler im Zweikampf auf einen anderen (computergesteuerten) Spieler trifft. Spielziel ist es eine feste Anzahl an zufällig generierten Gegnern (derzeit 100) zu besiegen. Jede Begegnung wird durch einen Raum repräsentiert in dem ein neuer Gegner vorgesetzt wird. Um seinen Gegner zu besiegen stehem jedem Teilnehmer eine Reihe von Aktionen zur Verfügung.

=Dungeon Koala=
[[Datei:Dungeonkoala_gui.png|thumbnail|Die Oberfläche]]
"Dungeon Koala" ist ein Spiel das wir uns im Rahmen des Projekts "Lernende Agenten" ausgedacht haben, um einen Agenten daran lernen zu lassen. Es ist ein rundenbasiertes Spiel, bei dem ein Spieler im Zweikampf auf einen anderen (computergesteuerten) Spieler trifft. Spielziel ist es eine feste Anzahl an zufällig generierten Gegnern (derzeit 100) zu besiegen. Jede Begegnung wird durch einen Raum repräsentiert in dem ein neuer Gegner vorgesetzt wird. Um seinen Gegner zu besiegen stehem jedem Teilnehmer eine Reihe von Aktionen zur Verfügung.

==Spielmechaniken==

===Charaktereigenschaften===
Jeder am Kampf teilnehmende Charakter wird durch folgende Eigenschaften beschrieben:
{| class="filehistory"
|-
! Eigenschaft !! Beschreibung
|-
| Name || Der Name des Charakters
|-
| Health || Repräsentiert die Gesundheit des Charakters, ist ein Wert von 0 bis 100
|-
| Energy || Der Energie-Level ist ein Bonus der auf die Attack-Power draufgerechnet wird und verstärkt somit das Angreifen, Heilen oder Blocken. Der Wert wird von den Aktionen die davon profitieren konsumiert, also verbraucht. Der Energie-Level geht von 0 bis 3.
|-
| Block-Level || Stellt die Stärke des Blocks dar. Der Block-Level wird beim Blocken aus dem Energielevel übernommen und um 1 erhöht. Wird der Charakter angegriffen, wird der Angriff basierend auf dem Block-Level reduziert.
|-
| Attack-Power || Der Grundwert für die Angriffskraft (und trotz des Namens auch für das Heilen)
|-
| Element || Das Element des Charakters. Es gibt "Feuer", "Wasser" und "Pflanze". Elementtypen können gewechselt werden und versehen die Angriffe des Charakters mit dem eigenen Element.
|-
| Behaviour || Das Verhalten des Charakters. Durch das Verhalten wird die Aktion gewählt, die der Charakter durchführen möchte.
|}

===Aktionen===
Jeder Charakter kann in jeder Runde eine der folgenden Aktionen wählen:
{| class="filehistory"
|-
! Aktion !! Beschreibung
|-
| Attack || Greift den Gegner an. Für die Schadensberechnung werden unterschiedliche Faktoren berücksichtigt: der eigene Elementtyp, der gegnerische Elementtyp, der Elementtyp des Raumes, der eigene Energie-Level, die eigene Attack-Power und der gegnerische Block-Level. Konsumiert den Energie-Level.
|-
| Heal || Heilt sich selbst. Diese Aktion profitiert auch vom Energie-Level. Konsumiert den Energie-Level.
|-
| Block || Blockiert den nächsten Angriff (und reduziert somit den Schaden). Konsumiert den Energie-Level.
|-
| Charge || Lädt den Energie-Level um eine Stufe auf (bis maximal 3).
|-
| Fire || Wechselt den Element-Typen des Charakters zum Typ "Feuer".
|-
| Water || Wechselt den Element-Typen des Charakters zum Typ "Wasser".
|-
| Grass || Wechselt den Element-Typen des Charakters zum Typ "Pflanze".
|}

===Raum===
Ein Raum repräsentiert eine Begegnung mit einem Gegner. Räume können auch Einwirkung auf den Kampf haben. Derzeit gibt es folgende Räume:
{| class="filehistory"
|-
! Raum !! Beschreibung
|-
| Default-Raum || Hat keinen besonderen Effekt auf den Kampf.
|-
| Feuer-Raum || Verstärkt Angriffe, Verteidigung und Heilen vom Typ Feuer um 20%.
|-
| Wasser-Raum || Verstärkt Angriffe, Verteidigung und Heilen vom Typ Wasser um 20%.
|-
| Pflanzen-Raum || Verstärkt Angriffe, Verteidigung und Heilen vom Typ Pflanze um 20%.
|}

===Behaviour===
Ein Behaviour steuert den Charakter dem sie zugeordnet ist. Für einen menschlichen Spieler gibt es eine grafische Benutzeroberfläche, die technisch für das Spiel nichts anderes ist. Für die Gegner, die vom Spiel erzeugt werden, gibt es folgende unterschiedliche Behaviours:
{| class="filehistory"
|-
! Behaviour!! Beschreibung
|-
| analytisch || Ein ausprogrammiertes Verhalten, das in jedem Zustand die Aktion wählt, die von uns als Entwicklern als am Sinnvollsten empfunden wird.
|-
| ATTACK EVERYTHING! || Führt immer nur die Aktion zum Angreifen durch.
|-
| random || Ein Verhalten, welches seine Aktionen zufällig wählt.
|-
| First Defense Behaviour || Ein Verhalten welches nur darauf bedacht ist selbst nicht zu sterben und nur dann angreift, wenn es sich selbst nicht bedroht fühlt.
|-
| Max Dps Behaviour || Ein Verhalten bei dem die Idee war, dass es ohne Rücksicht auf Verluste versucht den maximalen Schaden zu verursachen. Leider wurde das Behaviour beim Ausbau der Spiel-Features zu wenig gepflegt, weswegen es etwas in die Tage gekommen ist.
|}

===GUI===
[[Datei:dungeonkoala_gui_explanation.png|thumbnail|Die Benutzeroberfläche mit Erklärung]]
# Der Fortschrittsbalken, zeigt an welchen Raum der Spieler zuletzt besiegt hat und wieviele er noch vor sich hat
# Das Spielfeld, hier wird der Raumtyp visualisiert und Animationen gezeichnet
# Der Spieler, zeigt das Icon des Spielers, seinen Namen, sein Behaviour, Statuseffekte und den Lebensbalken an
# Der Gegner, zeigt das Icon des Spielers, seinen Namen, sein Behaviour, Statuseffekte und den Lebensbalken an
# Die Aktionsauswahl, ist nur aktiv, wenn das Spiel von einem Menschen gespielt wird
# Statusleiste und Geschwindigkeitskontrolle, hier kann festgelegt werden, wie schnell die Animationen ablaufen sollen. Mit den Pause- / Play-Buttons kann rundenweise abgespielt werden (besonders interessant, um ein computergesteuertes Behaviour nachzuvollziehen)

==Reinforcement Learning / Q-Learning==
Beim Q-Learning Verfahren handelt es sich um eine mögliche Implementation des Reinforcement Learning.
Der grundlegende Aufbau erfordert 3 Dinge:
* Einen [[#Q-Learning Agent | Q-Learning Agenten]]
* Eine [[#Q-Learning Environment | Q-Learning Environment]]
* Eine [[#Q-Werte Tabelle | Q-Werte Tabelle]]

===Q-Learning Agent===
Hält seine Q-Werte Tabelle und eine Policy nach welcher er Entscheidungen trifft.

===Q-Learning Environment===
Ist die Schnittstelle zwischen Agent und "realer" Umwelt. <br />
Sie observiert die Umwelt und leitet daraus einen Zustand ab. <br />
Nachdem eine Aktion ausgeführt wurde entscheidet die Environment inwiefern der Agent für die gewählte Aktion belohnt/bestraft wird.<br />

===Q-Werte Tabelle===
Enthält einen [[#Q-Wert | Q-Wert]] für jedes mögliche Zustands/Aktionen Paar 

====Q-Wert====
Stellt die Bewertung einer Aktion zu einem Zustand dar.

===Policy===
Ist der Bestandteil des [[#Q-Learning Agent | Q-Learning Agent]] welcher vorgibt nach welchem Vorgehen die nächste Aktion anhand der übergebenen [[#Q-Werte Tabelle | Q-Werte Tabelle]] ausgewählt wird.<br />
Der Policy werden um eine Entscheidung zu treffen immer übergeben:
* Der aktuelle Zustand der [[#Q-Learning Environment | Q-Learning Environment]]
* Eine Menge möglicher Aktionen welche aus dem Zustand heraus getätigt werden können
* Die [[#Q-Werte Tabelle | Q-Werte Tabelle]]

Momentan umfasst das Projekt die folgenden Policies:

====StaticPolicy====
Wählt immer die Aktion mit dem höchsten zugehörigen [[#Q-Wert | Q-Wert]] aus.

====RandomPolicy====
Wählt ihrem Namen entsprechend aus der Menge der möglichen Aktionen völlig zufällig.

====StaticExplorationPolicy====
Ist eine Erweiterung der "StaticPolicy", welche es ermöglicht zusätzlich zum statischen Verhalten einen festen Anteil aller Entscheidungen zufällig zu treffen.<br />
Dadurch werden mehr Zustands/Aktionen-Paare ausprobiert, es werden also sinngemäß neue Möglichkeiten erforscht.

====DynamicExplorationPolicy====
Analog zu der "StaticExplorationPolicy" ermöglicht die DynamicExplorationPolicy es Aktionen gewichtet nach ihren [[#Q-Wert | Q-Werten]] zu wählen.<br />
Somit werden zwar neue Möglichkeiten erforscht, jedoch bei negativen Ergebnissen immer seltener.

===Feature===
Beschreibt einen Satz an Informationen die dem Agenten zur Verfügung stehen um seine Entscheidungen zu treffen.<br />
Jedes Feature hat Unterschiedliche Ausprägungen.

====Player Charge====
Der Aktuelle Charge Level des Agenten
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Kein Charge
|-
| 1 || Level 1
|-
| 2 || Level 2
|-
| 3 || Level 3
|}

====Enemy Charge====
Der Aktuelle Charge Level des Gegners
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Kein Charge
|-
| 1 || Level 1
|-
| 2 || Level 2
|-
| 3 || Level 3
|}

====Player Almost Dead====
Mit dieser Information soll der Agent entscheiden können wie gefährlich der nächste Angriff des Gegners ist
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Keine Bedrohung
|-
| 1 || Der Agent besitzt nur 50% Leben
|-
| 2 || Der nächste Angriff des Gegners wird den Agenten töten
|}

====Enemy Almost Dead====
Mit dieser Information soll der Agent entscheiden können wie gefährlich sein nächste Angriff für den Gegners ist
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Keine Bedrohung
|-
| 1 || Der Gegner besitzt nur 50% Leben
|-
| 2 || Der nächste Angriff des Agenten wird den Gegner töten
|}

====Player Health====
Mit dieser Information soll der Agent entscheiden können wie schwer er verletzt ist
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Der Agent hat <100% Leben
|-
| 1 || Der Agent hat <  80% Leben
|-
| 2 || Der Agent hat <  60% Leben
|-
| 3 || Der Agent hat <  40% Leben
|-
| 4 || Der Agent hat <  20% Leben
|-
| 5 || Der Agent ist tot
|}

====Enemy Health====
Mit dieser Information soll der Agent entscheiden können wie schwer sein Gegner verletzt ist
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Der Gegner hat <100% Leben
|-
| 1 || Der Gegner hat <  80% Leben
|-
| 2 || Der Gegner hat <  60% Leben
|-
| 3 || Der Gegner hat <  40% Leben
|-
| 4 || Der Gegner hat <  20% Leben
|-
| 5 || Der Gegner ist tot
|}

====Player Element====
Mit dieser Information kennt der Agent sein aktuelles Element
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Der Agent hat das Element Wasser
|-
| 1 || Der Agent hat das Element Feuer
|-
| 2 || Der Agent hat das Element Gras
|}

====Enemy Element====
Mit dieser Information kennt der Agent das Element seines Gegners
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Der Gegner hat das Element Wasser
|-
| 1 || Der Gegner hat das Element Feuer
|-
| 2 || Der Gegner hat das Element Gras
|}

====Enemy Behaviour====
Mit dieser Information kann der Agent verschiedene Gegner unterscheiden <br />
(Die unten angegebenen Werte sind nur korrekt wenn alle Behaviours aktiviert sind.)
{| class="filehistory"
|-
! Wert !! Bedeutung
|-
| 0 || Der Gegner hat analytisches Verhalten
|-
| 1 || Der Gegner greift nur an
|-
| 2 || Der Gegner handelt zufällig
|-
| 3 || Der Gegner charged und greift dann an
|}

==Projektverlauf==

===1. Initiierungsphase===
*Projekt wurde an generische Dungeon Crawler angelehnt

*4 Mitglieder im Team

*Trello wird zur Teamsyschronisation verwendet

*Github wird zur versionierung verwendet

===2. Einstiegsphase=== 
*Das Ziel des Projektes ist ein Agent mit verstärkendem Lernen (Q-Learning) 

*Entwurf eines Spielrahmens

*Vorstellung: Spielstill wie 2D Pokemon(Gameboy): In jedem Raum kämpft ein Spieler gegen einen Gegner mit unterschiedlichen Aktionen und unterschiedlichen Verhalten.

*Architektur festlegen

*UML: Anwendungsfalldiagramm und Klassendiagramm erstellen

===3. Planungsphase===
21.03.2016

*User-Interface und Agent entwickelt

04.04.2016

*UI um Kampfsystem darzustellen

*Policy und Q-Learning Environment implementiert

11.04.2016

*Zustandsbereich der Q-Value Table generieren

*Features implementieren

*Verhalten: Random, Analytisch, Attack Everything, Maximal damage, Defense erstellen

18.04.2016

*Mehre Features überlegen und implementieren

*Elementar-Typen: Feuer, Wasser, Gras für den Player und den Gegner.

25.04.2016

*Controller für den Agent-Debugger auf UI

*Konfiguration von Q-Learning-Iteration und Policy auf UI 

*Auswahl von Feature auf UI

02.05.2016

*Verbesserung von Aussehen des UIs und Q-Value Table

*Neues Feature erstellen: Unterscheid von Gegner-Verhalten erkennen

09.05.2016

*Konfiguration von Exploration-Policy 

*Erstellung von Element Feature

17.05.2016

*Animationen von Element-Attack und Block einbauen

*Verbesserung von der Grafik für das Spiel

*Verbesserung von Leistung des Spiels

23.05.2016

*Animationen mit Spielgeschwindigkeit koppeln

*Logger erweitern um Logging Level

30.05.2016
*Statistik View für Debug GUI

06.06.2016
*4 unterschiedliche Räume (Standard-, Feuer-, Gras- und Wasserraum) erstellen
*Neue Feature: Alternatives Lebensfeature, Raumtyp-Feature

===4. Auswertungsphase===
*Siehe unten [[#Ergebnisse | Ergebnisse]]

===5. Weiterführungsphase===
*Wikipedia von Dungeon Koala dokumentieren

==Ergebnisse==

===Dungeon Koala Experimente===
{| class="filehistory"
|-
! Lern Iterationen !! Lern Policy !! Execution Policy !! Win Rate !! Average Actions !! Max Kills !! Feature Set
|-
| 0 || SnakeoilPolicy || SnakeoilPolicy || -0% || 42 || 1337 || {}
|-
|0||-||RandomPolicy||0||277||2||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|0||-||StaticPolicy||0||19||5||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|0||-||StaticExplorationPolicy||0||21||7||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|0||-||DynamicExplorationPolicy||0||44||5||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|1000||StaticPolicy||StaticPolicy||0||10||37||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|1000||StaticExplorationPolicy||StaticPolicy||0||7||20||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|1000||StaticExplorationPolicy||StaticExplorationPolicy||0||9||13||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|1000||StaticExplorationPolicy||DynamicExplorationPolicy||0||19||11||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|1000||StaticPolicy||StaticPolicy||0||10||20||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|1000||StaticExplorationPolicy||StaticPolicy||0||11||17||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|2000||StaticPolicy||StaticPolicy||0,1396||9||100||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|2000||StaticExplorationPolicy||StaticPolicy||0||11||17||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|2000||StaticExplorationPolicy||StaticExplorationPolicy||0||13||13||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|2000||StaticExplorationPolicy||DynamicExplorationPolicy||0||35||6||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|2000||StaticPolicy||StaticPolicy||0||10||34||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|2000||StaticExplorationPolicy||StaticPolicy||0||10||20||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|3000||StaticPolicy||StaticPolicy||0||9||28||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|3000||StaticExplorationPolicy||StaticPolicy||0||8||40||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|3000||StaticExplorationPolicy||StaticExplorationPolicy||0||10||25||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|3000||StaticExplorationPolicy||DynamicExplorationPolicy||0||25||7||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|3000||StaticPolicy||StaticPolicy||0||9||50||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|3000||StaticExplorationPolicy||StaticPolicy||0||9||25||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|5000||StaticPolicy||StaticPolicy||0||10||74||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|5000||StaticExplorationPolicy||StaticPolicy||0||7||69||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|5000||StaticExplorationPolicy||StaticExplorationPolicy||0||9||37||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|5000||StaticExplorationPolicy||DynamicExplorationPolicy||0||27||8||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|5000||StaticPolicy||StaticPolicy||0||9||32||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|5000||StaticExplorationPolicy||StaticPolicy||0||8||34||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|10000||StaticPolicy||StaticPolicy||0||9||77||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|10000||StaticExplorationPolicy||StaticPolicy||0,0849||7||100||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|10000||StaticExplorationPolicy||StaticExplorationPolicy||0||9||81||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|10000||StaticExplorationPolicy||DynamicExplorationPolicy||0||27||10||{Self Health, Self/Enemy Charge, Self/Enemy Element}
|-
|10000||StaticPolicy||StaticPolicy||0||9||84||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|10000||StaticExplorationPolicy||StaticPolicy||0,01||8||100||{Self Health, Self/Enemy Charge, Self/Enemy Element, Enemy Behaviour}
|-
|50000||StaticExplorationPolicy||StaticPolicy||0,0073||6||100||{Self Health, Self/Enemy Element, Self/Enemy Charge, Enemy Behaviour}
|-
|50000||StaticExplorationPolicy||StaticPolicy||0,24||5||100||{Self/Enemy Health, Self/Enemy Element, Self/Enemy Charge, Enemy Behaviour}
|-
|50000||StaticExplorationPolicy||StaticPolicy||0,0975||5||100||{Self Health, Enemy almost Dead, Self/Enemy Element, Self/Enemy Charge, Enemy Behaviour}
|-
|50000||StaticExplorationPolicy||StaticPolicy||0||5||77||{Self/Enemy almost Dead, Self/Enemy Element, Self/Enemy Charge, Enemy Behaviour}
|-
|50000||StaticExplorationPolicy||StaticPolicy||0,0022||6||100||{Self Health, Self/Enemy Element, Self/Enemy Charge}
|-
|50000||StaticExplorationPolicy||StaticPolicy||0,5111||5||100||{Self/Enemy Health, Self/Enemy Element, Self/Enemy Charge}
|}

==Ideen zum Ausbau==
Obwohl das Projekt einen vorzeigbaren Stand hat, gibt es noch einige Ausbaumöglichkeiten. Das ist quasi die Antwort auf die Frage "Wo weitermachen?".

===Spiel===
* Andere Raumarten, die unterschiedliche Einwirkungen auf den Kampf haben können, z.B.
** Sandsturm-Raum (Angriffe treffen z.B. nicht immer)
* Die Räume als Karte aufbauen, sodass man nach jedem Raum selbst wählen kann in welchen Raum man dann möchte. Das könnte sich vom Konzept an "Binding of Isaac" anlehnen.
** Das ermöglicht es z.B. auch Räume zu verwenden, in denen nicht gekämpft wird (Shops, Schätze, etc.)
** Möglicherweise gibt es einen Bossgegner den es zu besiegen gilt (um dem Spielziel mal einen Sinn zu geben)
* Den Kampf komplexer machen
* Gegenstände, die man nur eine gewisse Häufigkeit einsetzen kann
** z.B. Heiltränke statt Heilen-Angriff
* Limitationen für Aktionen
** z.B. Nicht beliebig oft heilen, oder Element-Typ wechseln können
* Leveling-System (Experience)
* Sinnvolles Balancing (insbesondere falls durch neue Features etwas "aus dem Ruder laufen" sollte)

===Agent===
* Das QLearning um Modelbased erweitern
* Analysieren, warum der Agent bei mehr Iterationen nicht konstant besser wird und das beheben
* Den Lernerfolg des Agenten Speichern / Laden können
* Einen ausgelernten Agenten als Gegner(-Behaviour) einsetzen
* Einen Agenten mit Neuronalen Netzen implementieren

==Trivia==
Ursprünglich sollte das Projekt einfach nur "Dungeon-Crawler" heißen. Das ist eine Pen-and-Paper/Tabletop Spielart, bei der einem Adventurer (Spieler) in jedem Raum ein Gegner vorgesetzt wird. Durch Übersetzungsprobleme zu einem unserer Gruppenmitglieder wurde daraus "Dungeon-Koala". Nachdem das herauskam fanden wir die Idee so witzig, dass wir das Projekt gleich umbenannt und einen Koala als Maskottchen gemalt haben, der dem Spieler als Icon angezeigt wird.
Auch interessant ist vielleicht, dass uns bei der Entwicklung gewisse Ähnlichkeiten zu dem Spiel "Pokemon" aufgefallen sind. Wir distanzieren uns jedoch in jeglicher Form von diesem Franchise.
