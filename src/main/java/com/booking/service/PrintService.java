package com.booking.service;

import java.text.DecimalFormat;
import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class PrintService {
    private static DecimalFormat n = new DecimalFormat("###,###,###");
    public static void printMenu(String title, String[] menuArr) {
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);
            num++;
        }
    }

    public static String printServices(List<Service> serviceList) {
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public static void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.printf("| %-4s | %-7s | %-14s | %-50s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+=======================================================================================================================================+");

        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-7s | %-14s | %-50s | %-15s | %-15s | %-10s |\n",
                num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), n.format(reservation.getReservationPrice()), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
        System.out.println();
    }

    public static void showAllCustomer(List<Person> personList) {
        System.out.printf("| %-7s | %-11s | %-15s | %-15s | %-15s |\n",
        "ID", "Nama", "Alamat", "Membership", "Uang");
        System.out.println("+=============================================================================+");
        
        personList.stream()
                .filter(person -> person instanceof Customer)
                    .map(person -> (Customer) person)
                    .forEach(customer -> {
                    System.out.printf("| %-7s | %-11s | %-15s | %-15s | %-15s |\n",
                    customer.getId(), customer.getName(), customer.getAddress(), customer.getMember().getMembershipName(), "Rp " + n.format(customer.getWallet()));
                });
                System.out.println();
    }

    public static void showAllEmployee(List<Person> employeeList) {
        System.out.printf("| %-6s | %-11s | %-15s | %-15s |\n",
        "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("+==========================================================+");
        
        employeeList.stream()
                .filter(person -> person instanceof Employee)
                .map(person -> (Employee) person)
                .forEach(employee -> {
                    System.out.printf("| %-6s | %-11s | %-15s | %-15s |\n",
                    employee.getId(), employee.getName(), employee.getAddress(), employee.getExperience());
                });
                System.out.println();
    }

    public static void showAllServices(List<Service> serviceList){
        System.out.printf("| %-4s | %-8s | %-20s | %11s |\n",
                "No.", "ID", "Nama", "Harga");
        System.out.println("+====================================================+");

        int num = 1;
        DecimalFormat n = new DecimalFormat("###,###,###");
        for (Service services : serviceList) {
            System.out.printf("| %-4s | %-8s | %-20s | %11s |\n",
            num, services.getServiceId(), services.getServiceName(), "Rp " + n.format(services.getPrice()));
            num++;
        }
    }

    public static void showHistoryReservation(List<Reservation> reservationList) {
        System.out.printf("| %-7s | %-14s | %-50s | %-15s | %-15s | %-10s |\n",
                "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+=================================================================================================================================+");
        double total = reservationList.stream()
                .filter(reservation -> "Finish".equals(reservation.getWorkstage()))
                .mapToDouble(Reservation::getReservationPrice)
                .sum();
        reservationList.stream()
                .filter(reservation -> "Finish".equals(reservation.getWorkstage()))
                .forEach(reservation -> {
                    System.out.printf("| %-7s | %-14s | %-50s | %-15s | %-15s | %-10s |\n",
                    reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), n.format(reservation.getReservationPrice()), reservation.getEmployee().getName(), reservation.getWorkstage());
                });
        System.out.println();
        System.out.println("Total keuntungan: Rp." + n.format(total));
    }
}