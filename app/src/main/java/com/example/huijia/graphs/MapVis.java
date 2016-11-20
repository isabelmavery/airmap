package com.example.huijia.graphs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.popup.Popup;
import com.esri.android.map.popup.PopupContainer;
import com.esri.core.map.Feature;
import com.esri.core.renderer.ClassBreak;
import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.renderer.Renderer;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISLayerInfo;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.popup.Popup;
import com.esri.android.map.popup.PopupContainer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.popup.PopupInfo;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.ags.query.QueryTask;


import static android.graphics.Color.*;

public class MapVis extends Activity {
    MapView mMapView;
    ArcGISFeatureLayer mFeatureLayer;
    GraphicsLayer mGraphicsLayer;
    String mFeatureServiceURL;
    private PopupContainer popupContainer;
    private PopupDialog popupDialog;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_vis);
        //setSupportActionBar(toolbar);


        TextView text = (TextView) findViewById(R.id.textView2);
        text.setTextColor(Color.rgb(199, 0, 57));
        TextView text1 = (TextView) findViewById(R.id.textView3);
        text1.setTextColor(Color.rgb(255, 195, 0));
        TextView text2 = (TextView) findViewById(R.id.textView4);
        text2.setTextColor(Color.rgb(40, 180, 99));
        //new LegendDialogFragment().show(getFragmentManager(), LegendDialogFragment.TAG);


        // Retrieve the map and initial extent from XML layout
        mMapView = (MapView) findViewById(R.id.map);
// Get the feature service URL from values->strings.xml
        mFeatureServiceURL = this.getResources().getString(R.string.featureServiceURL);
        ClassBreaksRenderer cityPopRenderer = getClassBreaksRenderer();

// Add Feature layer to the MapView
        mFeatureLayer = new ArcGISFeatureLayer(mFeatureServiceURL, ArcGISFeatureLayer.MODE.ONDEMAND);

        mFeatureLayer.setRenderer(cityPopRenderer);
        mMapView.addLayer(mFeatureLayer);
// Add Graphics layer to the MapView
        mGraphicsLayer = new GraphicsLayer();
/////

        ///////
        mMapView.addLayer(mGraphicsLayer);

        mMapView.setOnSingleTapListener(new OnSingleTapListener() {
                                            public void onSingleTap(float x, float y) {
                                                tapRequest(x, y);
                                            }
                                        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void tapRequest(float x, float y) {
        int[] fts = mFeatureLayer.getGraphicIDs(x, y, 10);
        if(fts.length>0){
            Feature f = mFeatureLayer.getGraphic(fts[0]);
            popupContainer = new PopupContainer(mMapView);
            Popup popup = mGraphicsLayer.createPopup(mMapView, 0, f);

            popupContainer.addPopup(popup);
            popupDialog = new PopupDialog(mMapView.getContext(), popupContainer);
            popupDialog.show();
        }

    }

    @NonNull
    private ClassBreaksRenderer getClassBreaksRenderer() {
        // instantiates a class break renderer
        ClassBreaksRenderer cityPopRenderer = new ClassBreaksRenderer();
// set the attribute field used by renderer to match values
        cityPopRenderer.setField("Mean_Ozone_ppm_2016");
// set the minimum population value = MIN_POP
        //cityPopRenderer.setMinValue(0);

// create a class break representing the first
// low class break
        ClassBreak lowClassBreak = new ClassBreak();
// set label as Low
        lowClassBreak.setLabel("Low");
// set class break max value
        lowClassBreak.setClassMaxValue(.04);
        lowClassBreak.setClassMinValue(0);

// set a pre-defined simple marker symbol
        lowClassBreak.setSymbol(new SimpleMarkerSymbol(Color.rgb(40, 180, 99), 8, SimpleMarkerSymbol.STYLE.CIRCLE));

// create two more class breaks representing the
// mid and max class breaks
        ClassBreak midClassBreak = new ClassBreak();
        midClassBreak.setLabel("Middle");
        midClassBreak.setClassMinValue(.04);
        midClassBreak.setClassMaxValue(.05);
        midClassBreak.setSymbol(new SimpleMarkerSymbol(Color.rgb(255, 195, 0), 8, SimpleMarkerSymbol.STYLE.CIRCLE));
        ClassBreak highClassBreak = new ClassBreak();
        highClassBreak.setLabel("High");
        highClassBreak.setClassMinValue(.05);
        highClassBreak.setClassMaxValue(.1);
        highClassBreak.setSymbol(new SimpleMarkerSymbol(Color.rgb(199, 0, 57), 8, SimpleMarkerSymbol.STYLE.CIRCLE));

// add class breaks to the class break renderer
        cityPopRenderer.addClassBreak(lowClassBreak);
        cityPopRenderer.addClassBreak(midClassBreak);
        cityPopRenderer.addClassBreak(highClassBreak);

// add the class break renderer to the graphics layer
        return cityPopRenderer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_vis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("MapVis Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class PopupDialog extends Dialog {
        private PopupContainer popupContainer;

        public PopupDialog(Context context, PopupContainer popupContainer) {
            super(context, android.R.style.Theme);
            this.popupContainer = popupContainer;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LinearLayout layout = new LinearLayout(getContext());
            layout.addView(popupContainer.getPopupContainerView(), android.widget.LinearLayout.LayoutParams.FILL_PARENT, android.widget.LinearLayout.LayoutParams.FILL_PARENT);
            setContentView(layout, params);
        }

    }
}
