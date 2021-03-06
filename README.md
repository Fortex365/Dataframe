# Very raw description:

## Třída `Index`
Soubor `Index.java` obsahuje třídu `Index` sloužící k indexaci libovolné sekvence hodnot.

Třída obsahuje následující vlastnosti:
* `Index.labels` - seznam klíču (labelů) - nesmí obsahovat duplicity (vyvolá vyjimku `ValueError`) - musí mít alespoň jeden prvek (jinak vyvolá vyjimku `ValueError`).
* `Index.name` - volitelná vlastnost obsahující název indexu, výchozí hodnota na `""`.

Třída obsahuje následující metody:
* `Index.getLoc(String key)` - přeloží klíč `key` z `Index.labels` na odpovídající index. Pokud není klíč přítomen vyvoláme výjimku `KeyError`.
* `Index.getName()` - vrací jméno Indexu
* `Index.getLabels()` - vrací labels Indexu
* `Index.len()` - vrací délku Indexu
* `Index.toString(String separator)` - vrací ve stringu `Index.labels` oddělené separátorem `separator`

## Třída `Series`
Soubor `Series.java` obsahuje třídu `Series`, která uchovává posloupnost hodnot indexovaných dle objektu třídy `Index`.

![Series](series.png)

Třída obsahuje následující vlastnosti:
* `Series.values` - seznam hodnot uložený v posloupnosti, musí obsahovat alespoň jeden prvek jinak vyvolá `ValueError`.
* `Series.index` - index sloužící k indexaci `Series.values`, musí být stejné délky jako `Series.values` jinak vyvolá `ValueError`. Pokud byla počáteční hodnota `null` vytvoříme index nový, `Index.labels` nastavíme na hodnoty `0` až `n` kde `n` je délka `Series.values`.

Třída obsahuje následující metody:
* `Series.get(String key)` - pokud `Series.index` obsahuje `key`, vrátí odpovídající hodnotu z `Series.values`, jinak vrací `null`.
* `Series.maxValue()` - nalezne maximální hodnotu z posloupnosti, detailní popis níže
* `Series.minValue()` - nalezne minimální hodnotu z posloupnosti, analogicky k max variantě
* `Series.apply(Function func)` - aplikuje libovolnou funkci na prvky posloupnosti, detailní popis níže
* `Series.getValues()` - vrací `Series.values`
* `Series.getIndex()` - vrací `Series.index` objekt
* `Series.len()` - vrací délku posloupnosti
* `Series.predicate(Function<T, Boolean> func)` - aplikuje predikátovou funkci na posloupnost a vrátí seznam pravdivostních hodnot 
* `Series.items()` - vrací iterátor (seznam) párů key:value castovaných do stringu
* `Series.shapeString()` - vrací "string tuple" rozměrů posloupnosti oddělených čárkou
* `Series.shape()` - vrací rozměry posloupnosti jako dvouprvkový int[]
* `Series.saveAsCsv(String filename)` - uloží instanci posloupnosti do souboru s daným názvem, default separator je `,`
* `Series.saveAsCsv(String filename, String separator)` - uloží instanci posloupnosti do souboru s daným názvem, volitelný separator

Dále bude možné provádět jednoduché operace na datech uložených v `Series`. Konkrétně:

### Metoda `Series.max()`/`Series.min()`
Maximální hodnota v `Series`.

```java
var users = new Index(new ArrayList<String>(Arrays.asList("user 1", "user 2", "user 3", "user 4")), name="names");
var cash_flow = new Series<Integer>(Arrays.asList(-100, 10000, -2000, 1100), index=users);
assert cash_flow.max() == 10000;
assert cash_flow.min() == -2000;
```

### Metoda `Series.apply(Function<T, T> func)` 
Která aplikuje funkci `func` na všechny prvky `Series` a vrátí `Series` novou (s vypočítanými hodnotami). Původní `Series` nemodifikuje!

```java
public static int squared(int a){
    return a * a;
}
var users = new Index(new ArrayList<String>(Arrays.asList("user 1", "user 2", "user 3", "user 4")), name="names");
var cash_flow = new Series<Integer>(Arrays.asList(-100, 10000, -2000, 1100), index=users);
var result = cash_flow.apply(Main::squared);
assert cash_flow.equals(result) == false;
assert result.getValues() == new ArrayList<Integer>(Arrays.asList(10000, 100000000, 4000000, 1210000));
```

## Třída `Dataframe`
Soubor `Dataframe.java` obsahuje třídu `Dataframe`, která slouží k reprezentaci tabulky dat. Tabulka je složena ze sloupců (alespoň jeden sloupec, každý sloupec je tvořen instancí třídy `Series`). Sloupce jsou indexovány pomoci instance třídy `Index`.

![DataFrame](dataframe.png)

Třída obsahuje následující vlastnosti:
* `Dataframe.values` - seznam instancí třídy `Series`, reprezentuje hodnoty sloupců, musí obsahovat alespoň jeden prvek jinak vyvolá `ValueError`.
* `Dataframe.columns` - index sloužící k indexaci `DataFrame.values`, musí být stejné délky jako `DataFrame.values` jinak vyvolá `ValueError`. Pokud byla počáteční hodnota `None` vytvoříme index nový, `Index.labels` nastavíme na hodnoty `0` až `n` kde `n` je délka `DataFrame.values`.

Třída obsahuje následující metody:
* `Dataframe.get(String key)` - pokud `Dataframe.columns` obsahuje `key`, vrátí odpovídající sloupec (`Series`) z `Dataframe.values`, jinak vrací `null`.
* `Dataframe.saveAsCsv(String filename)` - uloží instanci tabulky do souboru s daným názvem, default separator je `,`
* `Dataframe.saveAsCsv(String filename, String separator)` - uloží instanci tabulky do souboru s daným názvem, volitelný separator

```java
var users = new Index(new ArrayList<String>(Arrays.asList("user 1", "user 2", "user 3", "user 4")), name="names");

var salaries = new Series<Integer>(Arrays.asList(20000, 10000, 300000, 20000, 50000), index=users);
var names = new Series<String>(new ArrayList<String>(Arrays.asList("Lukas Novak", "Petr Pavel", "Pavel Petr", "Ludek Skocil")), index=users);
var cash_flow = new Series<Integer>(new ArrayList<Integer>(Arrays.asList(-100, 10000, -2000, 1100)), index=users);

var series = new ArrayList<Series>(Arrays.asList(names, salaries, cash_flow));
var index = new Index(new ArrayList<String>(Arrays.asList("names", "salary", "cash flow")));

var data = new DataFrame(series, columns=index);
assert data.get("salary") == salaries;
assert data.get("cash flow").max() == 10000;
```
