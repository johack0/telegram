package org.telegram.android.kernel;

import android.os.Process;
import org.telegram.android.actors.Actors;
import org.telegram.android.core.audio.AudioPlayerActor;
import org.telegram.android.core.audio.VoiceCaptureActor;
import org.telegram.threading.ActorSystem;

/**
 * Created by ex3ndr on 17.03.14.
 */
public class ActorKernel {

    private ActorSystem actorSystem;
    private VoiceCaptureActor voiceCaptureActor;
    private AudioPlayerActor audioPlayerActor;
    private ApplicationKernel kernel;

    public ActorSystem getActorSystem() {
        return actorSystem;
    }

    public ActorKernel(ApplicationKernel kernel) {
        this.kernel = kernel;
    }

    public void create() {
        actorSystem = new ActorSystem();

        actorSystem.addThread(Actors.THREAD_FS);
        actorSystem.addThread(Actors.THREAD_ENCODER);
        actorSystem.addThread(Actors.THREAD_AUDIO, Process.THREAD_PRIORITY_AUDIO);
        actorSystem.addThread(Actors.THREAD_COMMON);

        voiceCaptureActor = new VoiceCaptureActor(kernel.getApplication(), actorSystem);
        audioPlayerActor = new AudioPlayerActor(kernel.getApplication(), actorSystem);
    }

    public VoiceCaptureActor getVoiceCaptureActor() {
        return voiceCaptureActor;
    }

    public AudioPlayerActor getAudioPlayerActor() {
        return audioPlayerActor;
    }

    public void runKernel() {
        actorSystem.runThreads();
    }
}
