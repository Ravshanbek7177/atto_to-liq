package controller;

import container.ComponentContainer;
import db.DataBase;
import dto.Profile;
import enums.Role;
import enums.Status;

import service.ProfileService;

import java.util.InputMismatchException;

public class MainController {
    public void start() {
           DataBase.init();
        while (true) {
            menu();
            int action = ComponentContainer.getAction();
            switch (action) {
                case 1 -> {
                    login();
                }
                case 2 -> {
                    registration();
                }
                case 0 -> {
                    System.exit(0);
                }
            }

        }
    }

    private void registration() {
        System.out.print("Enter name >> ");
        String name = ComponentContainer.stringScanner.next();
        System.out.print("Enter surname >> ");
        String surname = ComponentContainer.stringScanner.next();
        System.out.print("Enter phone >> ");
        String phone = ComponentContainer.stringScanner.next();
        System.out.print("Enter password >> ");
        String password = ComponentContainer.stringScanner.next();
        Profile profile = new Profile();
        profile.setPassword(password);
        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        ComponentContainer.profileService.registration(profile);

    }

    private void login() {
        System.out.print("Enter phone :");
        String phone = ComponentContainer.stringScanner.next();
        System.out.print("Enter password :");
        String password = ComponentContainer.stringScanner.next();
        Profile profile = ComponentContainer.profileService.login(phone, password);
        if (profile == null) {
            System.out.println("password or login incorrect");
            return;
        }
        if (profile.getStatus().equals(Status.BLOCK)){
            System.out.println("PROFILE BLOCKED BY ADMINISTRATION");
            return;
        }
        if (profile.getRole().equals(Role.USER)) {
            profile.setStatus(Status.ACTIVE);
            ComponentContainer.profileRepository.updateProfileStatus(profile);
            ComponentContainer.profileController.start(profile);
        } else if (profile.getRole().equals(Role.ADMIN)) {
            ComponentContainer.adminController.start(profile);
        }
    }


    private void menu() {
        System.out.println("1.Login");
        System.out.println("2.Registration");
        System.out.println("0.Exit");
    }
}
