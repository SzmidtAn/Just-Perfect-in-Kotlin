package com.example.girlsshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.girlsshopping.data.FacebookLogin;
import com.example.girlsshopping.products.AddProductActivity;
import com.example.girlsshopping.products.Product;
import com.example.girlsshopping.products.ProductCategory;
import com.example.girlsshopping.products.ProductDetailActivity;
import com.example.girlsshopping.products.ProductDetailFragment;
import com.example.girlsshopping.products.ProductsDataBase;
import com.example.girlsshopping.ui.favourites.FavouritesFragment;
import com.example.girlsshopping.ui.gallery.GalleryFragment;
import com.example.girlsshopping.ui.home.HomeFragment;
import com.example.girlsshopping.ui.message.MessageFragment;
import com.example.girlsshopping.ui.searching.SearchingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnProductClickedListener{
    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences prefs = null;


    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);


        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs

            addProductsToDataBase();


            prefs.edit().putBoolean("firstrun", false).commit();
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton floatingActionButton=findViewById(R.id.addFloatingActionBar);

        setSupportActionBar(toolbar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    private void addProductsToDataBase() {

        List<Product> productsAdd=new ArrayList<>();
        productsAdd.add(new Product("Sukienka", "SUKIENKA Z KORONKĄ\n" +
                "\n" +
                "Delikatna, bawełniana mini sukienka stworzona na letnie dni i ciepłe wieczory. Świetnie sprawdzi się również  podczas eleganckich okazji takich jak rustykalne wesele czy romantyczna kolacja z ważną osobą.\n" +
                "\n" +
                "Sukienka lekka i przewiewna. Model delikatnie prześwitujący.", ProductCategory.CLOTHES, "55", "https://a.allegroimg.com/s1024/114c30/eb3e31304a8b86393ad5c65c2873", "M", "Reserved", "nowe"));
        productsAdd.add(new Product("Okulary przeciwsłoneczne", "\n" +
                "Polaryzacja\n" +
                "\n" +
                "Specjalny filtr soczewkowy, który powstrzymuje promienie słoneczne przed bezpośrednim przedostawaniem się do Twojego oka, co utrudniałoby Ci widzenie. Docenisz to przede wszystkim podczas letnich dni, jak również podczas prowadzenia pojazdu, spacerowania po górach czy uprawiania sportów zimowych.\n" +
                "\n" +
                "tech\n" +
                "Ray-Ban\n" +
                "\n" +
                "Od prawie ośmiu dekad Ray-Ban przesuwa granice zarówno w dziedzinie inżynierii optycznej, jak i popkultury. Od prezydentów po gwiazdy rocka i świata filmu, od artystów po modeli, okulary Ray-Ban niezmiennie towarzyszą ikonom kultury każdego pokolenia.\n" +
                "\n" +
                "tech\n" +
                "New Wayfarer Classic RB2132 901/58\n" +
                "\n" +
                "Ray-Ban Wayfarer to bezwatpienia jeden z najbardziej znanych modeli okularów przeciwslonecznych w historii. Pokazane po raz pierwszy w 1952 roku, Ray-Ban Wayfarer staly sie ikona mody dwudziestego wieku po ich zalozeniu przez legendy srebrnego ekranu. Audrey Hepburn nosila Ray-Ban Wayfarer w „Sniadaniu u Tiffany’ego” z 1961 roku, Tom Cruise nosil je w ,,Ryzykownym interesie” a Bob Dylan rzadko byl widywany bez Ray-Ban Wayfarer z ciemnymi szklami w okresie swojej swietnosci.", ProductCategory.ACCESSORIES, "220", "https://media.littlewoodsireland.ie/i/littlewoodsireland/MTHHT_SQ1_0000000004_BLACK_MDf/ray-ban-classic-wayfarer-sunglasses-black.jpg?$180x240_retinamobilex2$", "M", "RayBan", "idealny")
        );
        productsAdd.add(new Product("Buty roz. 38", "Buty trekkingowe Acotango Niebieskie\n" +
                "\n" +
                "Niebieski to nie tylko błękit nieba otaczający wszystkie andyjskie szczyty, z Aconcaguą na czele. To także kolor wody, która czasami przebrana jest za górski strumień, kiedy indziej za leśne jezioro, a jeszcze innym razem za nagłe oberwanie chmury pośrodku niekończącej się łąki. Jeśli jesteś jednym z tych, których nie odstrasza najgorsza nawet prognoza pogody, wybierz buty trekkingowe Acotango w kolorze niebieskim.\n" +
                "\n" +
                "Acotango: zdobywaj świat lekkim krokiem\n" +
                "\n" +
                "Acotango to połączenie słów Aconcagua, czyli nazwy najwyższego szczytu Andów i… argentyńskiego tanga. Nie ma w tym ani grama przypadku. Nasze buty trekkingowe pozwalają przemierzać szlaki lekkim, niemalże tanecznym krokiem. Bez względu na to, czy z nieba leje się letni żar, czy na ziemię spływają strugi deszczu.", ProductCategory.SHOES, "150", "https://a.allegroimg.com/s512/030ac4/7ef1642145a8b82fda3a872c61a8/BUTY-FILA-DISRUPTOR-II-PREMIUM-39-5-GRATIS", "M", "Reserved", "idealny"));
        productsAdd.add(new Product("Kurtka skórzna", "OPIS PRODUKTU - Kurtka Męska JKB450_2\n" +
                "Model JKB450_2 to lekka, skórzana kurtka w czarnym kolorze. Charakteru temu modelowi nadaje wyjątkowa skóra perforowana, która w połączeniu ze skórą licową tworzy nowoczesną, elegancką formę. Krótka skórzana stójka zapinana paskiem na napy, pomaga chronić przed wiatrem. Odpowiedni krój sprawia, że całość doskonale dopasowuje się do sylwetki. Polecamy dla mężczyzn szukających klasycznych form w nowej, niepowtarzalnej formie.\n" +
                "\n" +
                "Najważniejsze cechy produktu:\n" +
                "\n" +
                "wykorzystanie do produkcji najlepszej gatunkowo skóry perforowanej owczej-jagnięcej, cienkiej i przyjemnej w dotyku, a jednocześnie wytrzymałej\n" +
                "skóra garbowana roślinnie, barwiona barwnikami naturalnymi, niealergizująca i przyjazna dla zdrowia człowieka\n" +
                "kurtka zapinana na zamek\n" +
                "krótka stójka zapinana paskiem na nap\n" +
                "dwie kieszenie przednie otwarte\n" +
                "dwie praktyczne kieszenie wewnętrzne po obu stronach kurtki (zapinane na zamek)\n" +
                "kurtka wewnątrz wykończona przyjemną w dotyku satynową podszewką (możliwość zmiany koloru podszewki)\n" +
                "rękawy zapinane na napy\n" +
                " \n" +
                "KOLOR - CZARNY\n" +
                "Możliwość uszycia kurtki w innym kolorze. DOSTĘPNE KOLORY - KLIKNIJ\n" +
                "Nie jesteś pewien koloru/gatunku skóry? - napisz, wyślemy Ci próbnik!\n" +
                "\n" +
                "ROZMIAR - Dostępna pełna rozmiarówka (szczegóły poniżej). \n" +
                "Możliwość dopasowania rozmiarów do indywidualnych potrzeb klienta. \n" +
                "Korekta długości rękawa GRATIS! \n" +
                "\n" +
                "GWARANCJA - Na nasze produkty przysługuje gwarancja 12 MIESIĘCY", ProductCategory.ACCESSORIES, "190", "https://dstreet.pl/pol_ps_Kurtka-damska-skorzana-FINES-czarna-TY1189-27316_1.jpg", "XL", "Inne", "nowe"));
        productsAdd.add(new Product("Spodnie", "OPIS\n" +
                "\n" +
                " \n" +
                "\n" +
                "Czarne spodnie wykonane z przyjemnego materiału, który delikatnie się poddaje. Ich ozdobą jest zamek na prawej nogawce. Klasyczny krój sprawia, że spodnie pięknie się prezentują w stylizacji codziennej. Idealnym dopełnieniem do spodni będzie biała koszula Classic, bluzka Meghan, April, lub klasyczny t-shirt. Zainspiruj się!\n" +
                "\n" +
                " \n" +
                "\n" +
                "PRODUKT POLSKI\n" +
                "\n" +
                " \n" +
                "\n" +
                "KOLOR: białe, czarne, czerwone\n" +
                "\n" +
                " \n" +
                "\n" +
                "SKŁAD: 95% bawełna, 5% elastan\n" +
                "\n" +
                " \n" +
                "\n" +
                "ROZMIAR/DOPASOWANIE\n" +
                "\n" +
                " \n" +
                "\n" +
                "Wymiary modelki:\n" +
                "wzrost 170 cm\n" +
                "biust 89 cm\n" +
                "talia 76 cm\n" +
                "biodra 98 cm\n" +
                "Modelka ma na sobie rozmiar: 36", ProductCategory.CLOTHES, "80", "https://www.ivet.pl/userfiles/productlargeimages/product_180001.jpg", "M", "Nike", "nowe"));
           productsAdd.add(new Product("Słomiany kapelusz", "Kapelusz Jipijapa (Panama) to produkt pozyskiwany z liści palmy ekwadorskiej. Tegoroczna kolekcja panam została wykonana przez meksykańskich rzemieślników z Bécal. Są tam specjalne jaskinie, w których powstają panamy. W Meksyku kapelusz Panama nazywają właśnie sombrero Jipijapa. Określenie Jipijapa pochodzi od nazwy miasteczka w Ekwadorze o takiej samej nazwie. Tradycja wykonywania panam w Bécal wywodzi się właśnie z ekwadorskiej Jipijapy.\n" +
                "ROZMIAR KAPELUSZA: 59\n" +
                "SUROWIEC liście palmy ekwadorskiej\n" +
                "SPLOT brisa\n" +
                "FASON fedora\n" +
                "SZEROKOŚĆ RONDA 6 cm\n" +
                "WSTĄŻKA brązowa lub granatowa\n" +
                "Każdy z kapeluszy jest niepowtarzalny. Odcień palmy jest naturalny i każda sztuka delikatnie się różni. W niektórych egzemplarzach występuje dodatkowe zdobienie w główce kapelusza (patrz zdjęcia). Część z nich ma rondo o szer 6cm-6.5cm. Jest kilka sztuk z rondem 5.5cm. Rozmiar większości kapeluszy to 59. Kilka sztuk jest także w rozmiarach 60-61. Wyboru wstążki należy dokonać w komentarzu do zamówienia. To samy w przypadku kapelusza ze zdobieniem czy też bez.\n" +
                "\n" +
                "Wstążka jest wąska, charakterystyczna dla mesykańskiego sombrero Jipijapa. Dostępna w dwóch kolorach: granatowym i brązowym. \n" +
                "\n" +
                "Rozmiar kapelusza\n" +
                "\n" +
                "Zebranie miary na kapelusz jest banalnie proste. Mierzymy obwód głowy ok. 1-2 cm nad uchem i ok. 2-3 cm nad brwiami. Wszystko zależy od tego, gdzie chcemy, żeby kapelusz był osadzony. Uzyskany wymiar to Twój rozmiar kapelusza. Zalecam wykonanie pomiaru kilka razy. W przypadku uzyskania pośredniej wartości (x centymetrów i pół) radziłbym zaokrąglić pomiar w górę. Na szczęście kapelusz za duży o jeden rozmiar można szybko zmniejszyć w obwodzie, doszywając u modystki dodatkową tasiemkę pod potnik kapelusza\n" +
                "\n" +
                "Jak nosić?\n" +
                "\n" +
                "Zasadniczym elementem Panamy w fasonie fedora (borsalino) jest odpowiednio wyprofilowane rondo. Z przodu powinno być ono skierowane ku dołowi, a z tyłu wywinięte ku górze. Kapelusz nosimy prosto albo lekko przekrzywiony w lewo i delikatnie nasunięty na oczy.", ProductCategory.ACCESSORIES, "90", "https://img.wearmedicine.com/i/855x1290/RW18-CAD401_99X_F5.jpg?v=1553688487", "M", "Reserved", "nowe"));

        productsAdd.add(new Product("Kopertowa sukienka z jerseyu", "Modna sukienka damska z jerseyu sprawdzi się w Twojej szafie, jako uniwersalne ubranie! Z odpowiednimi dodatkami możesz wybrać ją do pracy, na rodzinne spotkanie, a nawet wesele lub studniówkę! Krój oparty jest na klasycznej konstrukcji koktajlowej. Górna część sukienki została przygotowana w stylu charakterystycznej koperty, subtelnie podkreślając dekolt i biust. Szerokie rękawy do łokci rozszerzane są ku dołowi, a dzięki specjalnie przygotowanym szwom, nie ograniczają swobody ruchów. Brązowoszara sukienka na wesele przełamana jest w talii elastyczną gumą. Odpowiednio przyszyta do niej spódnica tworzy romantyczne fale, opadające wzdłuż sylwetki. Sukienka sięga nad kolano. Ładnie układa się w czasie chodzenia, czy tańca. Miękki materiał nie ociera delikatnej skóry, a szwy nie pozostawiają na niej odcisków.\n" +
                "\n" +
                "Ponadczasowa sukienka w kolorze Taupe\n" +
                "Brązowoszary kolor \"Taupe\" wygląda oryginalnie, ładnie komponując się ze złotą biżuterią. Zwiewna sukienka dla kobiet nie wymaga praktycznie żadnych dodatków, aby zachwycać swoim wyglądem. Można dobrać do niej lekkie kolczyki, naszyjnik, czy bransoletkę, na stopy zakładając ulubione sandałki na obcasie. Luźno opadający materiał dobrze prezentuje się na każdym typie sylwetki. Długie rękawy tuszują niedoskonałości przedramion, z kolei szeroka spódnica świetnie zmniejsza wielkość ud i pośladków. W Mosquito sukienka dostępna jest w rozmiarach XS - L, a każdy z nich odpowiada cechom charakterystycznym damskiej figury. Model powstał w polskich szwalniach zgodnie z projektem Alicji Komar. Wyglądaj olśniewająco bez względu na okoliczności i zdecyduj się na sukienkę w kolorze Taupe, która podkreśli Twój naturalny urok!", ProductCategory.CLOTHES, "140", "https://loola.pl/environment/cache/images/0_0_productGfx_3043.jpg", "M", "Reserved", "nowe"));


        for (int i = 0; i <productsAdd.size() ; i++) {

        ProductsDataBase.getDataBase(getApplicationContext()).getProductDao().insert(productsAdd.get(i));
        }


    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }



    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            return true;
                            case R.id.action_searching:
                                SearchingFragment searchingFragment=new SearchingFragment();
                                openFragment(searchingFragment);
                            return true;
                            case R.id.action_favoriter:
                                FavouritesFragment favouritesFragment=new FavouritesFragment();
                                openFragment(favouritesFragment);
                            return true;
                            case R.id.action_messages:
                                MessageFragment messageFragment=new MessageFragment();
                            openFragment(messageFragment);
                            return true;

                    }
                    return false;
                }
            };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(), R.string.empty_aviseringar, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProductClicked(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(ProductDetailFragment.PRODUCTS_ID, id);

        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
