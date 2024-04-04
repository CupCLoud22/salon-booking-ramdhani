package com.booking.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class ReservationService {
    private static final Scanner input = new Scanner(System.in);
    private static DecimalFormat n = new DecimalFormat("###,###,###");

    public static void createReservation(List<Person> personList, List<Service> serviceList,
    List<Reservation> reservationList) {
        System.out.println("Membuat reservation");

        PrintService.showAllCustomer(personList);
        System.out.println();

        System.out.print("Silahkan Masukkan Customer Id : ");
        String customerId = input.nextLine();
        Customer customer = personList.stream()
                .filter(person -> person instanceof Customer)
                .map(preson -> (Customer) preson)
                .filter(preson -> preson.getId().equals(customerId))
                .findFirst()
                .orElse(null);
        
        if (customer == null) {
            System.err.println("Id customer tidak ditemukan");
            return;
        }
        
        PrintService.showAllEmployee(personList);
        System.out.println();
    
        System.out.print("Silahkan Masukkan Employee Id : ");
        String employeeId = input.nextLine();
        Employee employee = personList.stream()
                .filter(preson -> preson instanceof Employee)
                .map(preson -> (Employee) preson)
                .filter(preson -> preson.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    
        if (employee == null) {
            System.err.println("Id employee tidak ditemukan");
            return;
        }

        System.out.println("Service yang tersedia");

        PrintService.showAllServices(serviceList);
        System.out.println();

        System.out.print("Masukan nomor service (Pisahkan nomor dengan spasi) : ");
        String numberService = input.nextLine();
        String[] numbers = numberService.split(" ");
        System.out.println();

        List<Service> selectedService = new ArrayList<>();
        double totalReservationPrice = 0.0;

        for (String number : numbers) {
            int angka = Integer.parseInt(number.trim()) - 1;
            if (angka >= 0 && angka < serviceList.size()) {
                selectedService.add(serviceList.get(angka));
                totalReservationPrice += serviceList.get(angka).getPrice();
            }
        }

        // int i = 0;
        // i++;
        // String formattedReservationId = String.format("%02d", i);
        Reservation reservation = Reservation.builder()
                .reservationId("Rsv-0" + reservationList.size())
                .customer(customer)
                .employee(employee)
                .services(selectedService)
                .workstage("In Process")
                .reservationPrice(totalReservationPrice)
                .build();

        reservationList.add(reservation);
        System.out.println("Booking Berhasil!");
        System.out.println("Total Biaya Booking : Rp." + n.format(totalReservationPrice));
    }

    public static void editReservationWorkstage(List<Reservation> reservationList) {
        System.out.println("Mengubah wrokstage");
        PrintService.showRecentReservation(reservationList);
        System.out.println();
        System.out.print("Silahkan Masukkan Reservation Id : ");
        String reservationId = input.nextLine();
        Reservation reservation = reservationList.stream()
                .filter(resv -> resv.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);

        if (reservation == null) {
            System.err.println("Reservation tidak ada");
        }

        System.out.print("Selesaikan reservasi (Finish / Canceled) : ");
        String workstage = input.nextLine();

        if ("Finish".equalsIgnoreCase(workstage)) {
            double price = reservation.getReservationPrice();
            Customer customer = reservation.getCustomer();

            if (price != 0.0 && customer != null) {
                double currentWallet = customer.getWallet();

                if (currentWallet < price) {
                    System.err.println("Saldo tidak mencukupi");
                    return;
                }

                double wallet = currentWallet - price;
                customer.setWallet(wallet);
            } else {
                System.err.println("Customer tidak ditemukan");
                return;
            }
        }

        reservation.setWorkstage(workstage);
        System.out.println("Reservasi dengan id " + reservationId + " sudah " + workstage);
    }

    public static void getCustomerByCustomerId() {
        
    }
}