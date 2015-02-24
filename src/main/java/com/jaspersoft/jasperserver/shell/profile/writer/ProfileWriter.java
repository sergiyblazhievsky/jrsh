package com.jaspersoft.jasperserver.shell.profile.writer;

import com.jaspersoft.jasperserver.shell.profile.entity.Profile;
import com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration;
import com.jaspersoft.jasperserver.shell.profile.representer.SkipEmptyRepresenter;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.yaml.snakeyaml.DumperOptions.FlowStyle.BLOCK;

/**
 * @author Alexander Krasnyanskiy
 */
public class ProfileWriter {

    private ProfileConfiguration cfg;
    private String file;
    private Yaml yml;

    public ProfileWriter(String file) {
        this.file = file;
        init();
    }

    public ProfileWriter(ProfileConfiguration cfg, String file) {
        this.cfg = cfg;
        this.file = file;
        init();
    }

    /**
     * Configures Yaml instance.
     */
    private void init() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(BLOCK);
        options.setPrettyFlow(true);
        yml = new Yaml(new SkipEmptyRepresenter(), options);
    }

    /**
     * Writes/rewrite profile configuration.
     *
     * @param cfg given profile configuration.
     */
    //@SneakyThrows
    public void write(ProfileConfiguration cfg) throws IOException {

        if (cfg.getProfiles().size() == 1 && (cfg.getDefaultProfile() == null || cfg.getDefaultProfile().equals(""))) {
            for (Profile profile : cfg.getProfiles()) {
                cfg.setDefaultProfile(profile.getName());
            }
        }

        FileWriter writer = new FileWriter(file);
        yml.dump(cfg, writer);
        writer.flush();
        writer.close();
        polish(file, true);
    }

    @SneakyThrows
    private void polish(String file, boolean isSetEmpty) {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();

        char[] buf = new char[1024];
        while (br.read(buf) != -1) builder.append(new String(buf));

        //
        // fixme: please! find some cuter solution
        //
        builder.insert(0, "# ==========================================================================\n" +
                "# === Do not attempt to modify this file manually. All changes should be ===\n" +
                "# === done programmatically. In the case of manual modification, the     ===\n" +
                "# === correct performance of the application just cannot be guaranteed.  ===\n" +
                "# ==========================================================================\n");
        String str = builder.toString();
        str = str.replace("?", "-");
        str = str.replace("defaultProfile", "default");

        if (isSetEmpty) {
            str = str.replace("set", "");
            str = str.replace("!!", "");
        }

        str = str.replace(": null", "");
        str = str.replace("    \n", "");
        str = str.replace("\n\n", "");
        str = str.replace("\t", "");


        str = str.replace("!!set", "");
        str = str.replace("com.jaspersoft.jasperserver.shell.profile.entity.ProfileConfiguration", "---");
        str = str.replace("}", "");
        str = str.replace("{", "");
        str = str.replace("\0", "");

        FileWriter writer = new FileWriter(file);
        writer.write(str);
        writer.flush();
        writer.close();
    }
}