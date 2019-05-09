package com.r4.matkapp;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Util class to handle window resizing when a stage style set to
 * StageStyle.UNDECORATED. Created on 8/15/17.
 *
 * Modified and added comments by Eero Tuure
 *
 * @author Evgenii Kanivets
 */
public class ResizeHelper {

    private static boolean canMove = true;
    
    private ResizeHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static synchronized boolean getCanMove() {
        return canMove;
    }

    /**
     * Add resize listener to a specified Stage.
     *
     * @param stage
     */
    public static void addResizeListener(Stage stage) {
        addResizeListener(stage, 0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Add resize listener to a specified Stage with given constraints.
     *
     * @param stage
     * @param minWidth
     * @param minHeight
     * @param maxWidth
     * @param maxHeight
     */
    public static void addResizeListener(Stage stage, double minWidth, double minHeight, double maxWidth, double maxHeight) {
        ResizeListener resizeListener = new ResizeListener(stage);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);

        resizeListener.setMinWidth(minWidth);
        resizeListener.setMinHeight(minHeight);
        resizeListener.setMaxWidth(maxWidth);
        resizeListener.setMaxHeight(maxHeight);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, resizeListener);
        }
    }

    /**
     * Recursively add listeners for all Parents.
     *
     * @param node
     * @param listener EventHandler to be added.
     */
    private static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    static class ResizeListener implements EventHandler<MouseEvent> {

        private Stage stage;
        private Cursor cursorEvent = Cursor.DEFAULT;
        private int border = 10;
        private double startX = 0;
        private double startY = 0;

        // Max and min sizes for controlled stage
        private double minWidth;
        private double maxWidth;
        private double minHeight;
        private double maxHeight;
        
        public ResizeListener(Stage stage) {
            this.stage = stage;
        }

        private synchronized void setCanMove(boolean b) {
            canMove = b;
        }

        public void setMinWidth(double minWidth) {
            this.minWidth = minWidth;
        }

        public void setMaxWidth(double maxWidth) {
            this.maxWidth = maxWidth;
        }

        public void setMinHeight(double minHeight) {
            this.minHeight = minHeight;
        }

        public void setMaxHeight(double maxHeight) {
            this.maxHeight = maxHeight;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (MainApp.getWindow().isResizable()) {

                EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
                Scene scene = stage.getScene();

                double mouseEventX = mouseEvent.getSceneX();
                double mouseEventY = mouseEvent.getSceneY();

                if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                    mouseMoved(mouseEventX, mouseEventY, scene);
                } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType) || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
                    scene.setCursor(Cursor.DEFAULT);
                    setCanMove(true);
                } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType)) {
                    startX = stage.getWidth() - mouseEventX;
                    startY = stage.getHeight() - mouseEventY;
                } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) && !Cursor.DEFAULT.equals(cursorEvent)) {
                    setCanMove(false);
                    mouseDragged(mouseEvent, mouseEventX, mouseEventY);

                }
            }
        }

        private void mouseMoved(double mouseEventX, double mouseEventY, Scene scene) {

            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();

            if (mouseEventX < border && mouseEventY < border) {
                cursorEvent = Cursor.NW_RESIZE;
            } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                cursorEvent = Cursor.SW_RESIZE;
            } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                cursorEvent = Cursor.NE_RESIZE;
            } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                cursorEvent = Cursor.SE_RESIZE;
            } else if (mouseEventX < border) {
                cursorEvent = Cursor.W_RESIZE;
            } else if (mouseEventX > sceneWidth - border) {
                cursorEvent = Cursor.E_RESIZE;
            } else if (mouseEventY < border) {
                cursorEvent = Cursor.N_RESIZE;
            } else if (mouseEventY > sceneHeight - border) {
                cursorEvent = Cursor.S_RESIZE;
            } else {
                cursorEvent = Cursor.DEFAULT;
            }
            scene.setCursor(cursorEvent);
        }

        private void mouseDragged(MouseEvent mouseEvent, double mouseEventX, double mouseEventY) {
            if (!Cursor.W_RESIZE.equals(cursorEvent) && !Cursor.E_RESIZE.equals(cursorEvent)) {
                resizeY(mouseEvent, mouseEventY);
            }

            if (!Cursor.N_RESIZE.equals(cursorEvent) && !Cursor.S_RESIZE.equals(cursorEvent)) {
                resizeX(mouseEvent, mouseEventX);
            }
        }

        private void resizeY(MouseEvent mouseEvent, double mouseEventY) {
            double miniumHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
            if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.N_RESIZE.equals(cursorEvent)
                    || Cursor.NE_RESIZE.equals(cursorEvent)) {
                if (stage.getHeight() > miniumHeight || mouseEventY < 0) {
                    setStageHeight(stage.getY() - mouseEvent.getScreenY() + stage.getHeight());
                    stage.setY(mouseEvent.getScreenY());
                }
            } else {
                if (stage.getHeight() > miniumHeight || mouseEventY + startY - stage.getHeight() > 0) {
                    setStageHeight(mouseEventY + startY);
                }
            }
        }

        private void resizeX(MouseEvent mouseEvent, double mouseEventX) {
            double miniumWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
            if (Cursor.NW_RESIZE.equals(cursorEvent) || Cursor.W_RESIZE.equals(cursorEvent)
                    || Cursor.SW_RESIZE.equals(cursorEvent)) {
                if (stage.getWidth() > miniumWidth || mouseEventX < 0) {
                    setStageWidth(stage.getX() - mouseEvent.getScreenX() + stage.getWidth());
                    stage.setX(mouseEvent.getScreenX());
                }
            } else {
                if (stage.getWidth() > miniumWidth || mouseEventX + startX - stage.getWidth() > 0) {
                    setStageWidth(mouseEventX + startX);
                }
            }
        }

        private void setStageWidth(double width) {
            width = Math.min(width, maxWidth);
            width = Math.max(width, minWidth);
            stage.setWidth(width);
        }

        private void setStageHeight(double height) {
            height = Math.min(height, maxHeight);
            height = Math.max(height, minHeight);
            stage.setHeight(height);
        }
    }
}
