package com.test;

import cognitivej.vision.face.scenario.FaceScenarios;
import cognitivej.vision.overlay.CognitiveJColourPalette;
import cognitivej.vision.overlay.RectangleType;
import cognitivej.vision.overlay.builder.ImageOverlayBuilder;
import org.junit.Test;

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
}
