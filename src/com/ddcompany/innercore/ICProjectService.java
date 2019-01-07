package com.ddcompany.innercore;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class ICProjectService implements PersistentStateComponent<ICProjectService.State> {
    private State state = new State();

    public static ICProjectService get(Project project) {
        return ServiceManager.getService(project, ICProjectService.class);
    }

    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(State state) {
        this.state = state;
    }

    public String getModDirectory(){
        return state.modDir;
    }

    public void setModDir(String modDir){
        state.modDir = modDir;
    }

    public String getDevice(){
        return state.device;
    }

    public void setDevice(String device){
        state.device = device;
    }

    public boolean isRunInnerCore(){
        return state.isRunInnerCore;
    }

    public void setRunInnerCore(boolean value){
        state.isRunInnerCore = value;
    }

    public boolean isSmartPush(){
        return state.isSmartPush;
    }

    public void setSmartPush(boolean value){
        state.isSmartPush = value;
    }

    public String getLast(){
        return state.lastPushed;
    }

    public void setLast(String path){
        state.lastPushed = path;
    }

    public String getSerial(){
        return state.serial;
    }

    public void setSerial(String serial){
        state.serial = serial;
    }

    public class State {
        public String modDir = "";
        public String device = "";
        public String lastPushed = "";
        public String serial = "";
        public boolean isRunInnerCore = true;
        public boolean isSmartPush = false;

        public void init(){

        }
    }
}
