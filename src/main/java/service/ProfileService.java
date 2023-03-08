package service;

import container.ComponentContainer;
import dto.Profile;
import enums.Status;


import java.util.List;

public class ProfileService {


    public void registration(Profile profile) {
        //check
        if (profile.getPassword().length() < 6) {
            System.out.println("Iltimos password 6 yoki undan ortiq belgidan iborat bolsin");
            return;
        }
        // bu profile oldin qoshilganmi yoqmi tekshiramiz phone unique bolishi kerak
        Profile profile1 = ComponentContainer.profileRepository.getProfileByPhone(profile.getPhone());

        if (profile1 != null) {
            System.out.println("Bu numerdan allaqachon royxatdan otilgan");
            return;
        }
        ComponentContainer.profileRepository.registration(profile);
    }

    public Profile  login(String phone, String password) {
        //check
        if(password.length()<6){
            return null;
        }
        //.....
        return ComponentContainer.profileRepository.login(phone,password);

    }

    public List<Profile> profileList() {
         return ComponentContainer.profileRepository.profileList();
    }

    public void changeProfileStatus(String phone) {
        Profile profile = ComponentContainer.profileRepository.getProfileByPhone(phone);
        if (profile==null){
            System.out.println("Profile not found");
            return;
        }
        if (profile.getStatus().equals(Status.ACTIVE)){
            profile.setStatus(Status.BLOCK);
        }else if (profile.getStatus().equals(Status.BLOCK)){
            profile.setStatus(Status.ACTIVE);
        }
        ComponentContainer.profileRepository.updateProfileStatus(profile);
    }

    public void cardList() {

    }
}
