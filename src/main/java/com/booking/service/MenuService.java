package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = { "Tampilkan Data", "Membuat Reservasi", "Finish/Cancel Reservasi", "Exit"};
        String[] subMenuArr = { "Tampilkan Recent Reservation", "Tampilkan Customer", "Tampilkan  Employee", "Tampilkan History Reservation + Total Keuntungan", "Back To Main Menu"};

        int optionMainMenu;
        int optionSubMenu;

        boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("\nMain Menu", mainMenuArr);
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    do {
                        PrintService.printMenu("\nShow Data", subMenuArr);
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                PrintService.showRecentReservation(reservationList);
                                break;
                            case 2:
                                PrintService.showAllCustomer(personList);
                                break;
                            case 3:
                                PrintService.showAllEmployee(personList);
                                break;
                            case 4:
                                PrintService.showHistoryReservation(reservationList);
                                break;
                            case 0:
                                backToSubMenu = true;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    ReservationService.createReservation(personList, serviceList, reservationList);
                    break;
                case 3:
                    ReservationService.editReservationWorkstage(reservationList);
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
            }
        } while (!backToMainMenu);

    }
}