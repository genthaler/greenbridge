/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.web;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author ryan
 */
public class StoreMediaStrategyImpl implements StoreMediaStrategy {

    private File rootStorageDir;

    public StoreMediaStrategyImpl(File rootStorageDir ) {
        this.rootStorageDir = rootStorageDir;
    }

    public String store(MultipartFile media) throws IOException {
        UUID id = UUID.randomUUID();
        String uuid = id.toString();
        String prefix = uuid.substring(0, 2);
        String suffix = media.getOriginalFilename().substring(media.getOriginalFilename().lastIndexOf("."), media.getOriginalFilename().length());
        File destFolder = new File(rootStorageDir, prefix);
        destFolder.mkdirs();
        File destFile = new File(destFolder, uuid +  suffix);
        media.transferTo(destFile);
        return prefix + "/" + uuid + suffix;
    }



}
