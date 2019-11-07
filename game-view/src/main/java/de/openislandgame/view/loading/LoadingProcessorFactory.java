package de.openislandgame.view.loading;

import com.jukusoft.engine2d.basegame.loading.LoadingProcessor;

public class LoadingProcessorFactory {

    private LoadingProcessorFactory() {
        //
    }

    public static LoadingProcessor create() {
        LoadingProcessor loadingProcessor = new LoadingProcessor();
        loadingProcessor.addTask(new ModLoaderTask());
        loadingProcessor.addTask(new InitAssetManagerTask());

        return loadingProcessor;
    }

}
