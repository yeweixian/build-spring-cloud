package com.test;

import cognitivej.vision.face.scenario.FaceScenarios;
import cognitivej.vision.overlay.CognitiveJColourPalette;
import cognitivej.vision.overlay.RectangleType;
import cognitivej.vision.overlay.builder.ImageOverlayBuilder;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;

import java.net.URISyntaxException;

public class ImageTest {

    private static final String IMAGE_URL
            = "https://camo.githubusercontent.com/9d34b6988c96fdcc48e47c262ad31e3db723f2f7/68747470733a2f2f69776b656c6c792e66696c65732e776f726470726573732e636f6d2f323031362f30352f73637265656e2d73686f742d323031362d30352d31312d61742d31322d32322d33302e706e67";

    @Test
    public void testImageOverlay() {
//        FaceScenarios faceScenarios = new FaceScenarios(getProperty("azure.cognitive.subscriptionKey"),
//                getProperty("azure.cognitive.emotion.subscriptionKey"));
        FaceScenarios faceScenarios = new FaceScenarios("351185f674bb419fb0f6482cdeeb1766",
                "d8c2d9acffde46eda659662213aca572");
        ImageOverlayBuilder imageOverlayBuilder = ImageOverlayBuilder.builder(IMAGE_URL);
        imageOverlayBuilder.outlineFacesOnImage(faceScenarios.findFaces(IMAGE_URL), RectangleType.FULL,
                CognitiveJColourPalette.STRAWBERRY).launchViewer();
    }

    @Test
    public void testFaceDetect() throws URISyntaxException, HttpProcessException {
        URIBuilder uriBuilder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect");
        // Request parameters. All of them are optional.
        uriBuilder.setParameter("returnFaceId", "true");
        uriBuilder.setParameter("returnFaceLandmarks", "false");
        uriBuilder.setParameter("returnFaceAttributes",
                "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise");
        // Prepare the URI for the REST API call.
        System.out.println(uriBuilder.build());
        String result = HttpClientUtil.post(HttpConfig.custom()
                .url(uriBuilder.build().toString())
                .headers(HttpHeader.custom()
                        .contentType("application/json")
                        .other("Ocp-Apim-Subscription-Key", "351185f674bb419fb0f6482cdeeb1766")
                        .build())
                .json("{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}"));
        System.out.println(result);
    }
}
