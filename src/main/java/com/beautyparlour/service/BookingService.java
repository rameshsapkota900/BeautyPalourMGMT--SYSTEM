package com.beautyparlour.service;

import com.beautyparlour.dto.request.BookCourseRequest;
import com.beautyparlour.dto.request.BookServiceRequest;
import com.beautyparlour.dto.request.UpdateBookingStatusRequest;
import com.beautyparlour.entity.CourseBooking;
import com.beautyparlour.entity.ServiceBooking;
import com.beautyparlour.exception.ResourceNotFoundException;
import com.beautyparlour.repository.CourseBookingRepository;
import com.beautyparlour.repository.CourseRepository;
import com.beautyparlour.repository.ServiceBookingRepository;
import com.beautyparlour.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private CourseBookingRepository courseBookingRepository;

    @Autowired
    private ServiceBookingRepository serviceBookingRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Course Booking Methods
    public CourseBooking bookCourse(BookCourseRequest request) {
        // Verify course exists
        com.beautyparlour.entity.Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        CourseBooking booking = new CourseBooking(
                course.getParlourId(),
                request.getCourseId(),
                request.getClientName(),
                request.getPhone()
        );
        return courseBookingRepository.save(booking);
    }

    public List<CourseBooking> getCourseBookingsByClient(String clientName, String phone) {
        return courseBookingRepository.findByClientNameAndPhone(clientName, phone);
    }

    public List<CourseBooking> getCourseBookingsByParlour(UUID parlourId) {
        return courseBookingRepository.findByParlourId(parlourId);
    }

    public void cancelCourseBooking(UUID bookingId) {
        CourseBooking booking = courseBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Course booking not found"));
        
        if (booking.getStatus() != CourseBooking.BookingStatus.PENDING) {
            throw new RuntimeException("Only pending bookings can be cancelled");
        }
        
        booking.setStatus(CourseBooking.BookingStatus.CANCELLED);
        booking.setCancelReason("Cancelled by client");
        courseBookingRepository.save(booking);
    }

    public CourseBooking updateCourseBookingStatus(UUID bookingId, UpdateBookingStatusRequest request, UUID parlourId) {
        CourseBooking booking = courseBookingRepository.findByIdAndParlourId(bookingId, parlourId)
                .orElseThrow(() -> new ResourceNotFoundException("Course booking not found"));

        CourseBooking.BookingStatus status = CourseBooking.BookingStatus.valueOf(request.getStatus().toUpperCase());
        booking.setStatus(status);
        
        if (status == CourseBooking.BookingStatus.CANCELLED && request.getCancelReason() != null) {
            booking.setCancelReason(request.getCancelReason());
        }

        return courseBookingRepository.save(booking);
    }

    // Service Booking Methods
    public ServiceBooking bookService(BookServiceRequest request) {
        // Verify service exists
        com.beautyparlour.entity.Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        ServiceBooking booking = new ServiceBooking(
                service.getParlourId(),
                request.getServiceId(),
                request.getClientName(),
                request.getPhone()
        );
        return serviceBookingRepository.save(booking);
    }

    public List<ServiceBooking> getServiceBookingsByClient(String clientName, String phone) {
        return serviceBookingRepository.findByClientNameAndPhone(clientName, phone);
    }

    public List<ServiceBooking> getServiceBookingsByParlour(UUID parlourId) {
        return serviceBookingRepository.findByParlourId(parlourId);
    }

    public void cancelServiceBooking(UUID bookingId) {
        ServiceBooking booking = serviceBookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Service booking not found"));
        
        if (booking.getStatus() != ServiceBooking.BookingStatus.PENDING) {
            throw new RuntimeException("Only pending bookings can be cancelled");
        }
        
        booking.setStatus(ServiceBooking.BookingStatus.CANCELLED);
        booking.setCancelReason("Cancelled by client");
        serviceBookingRepository.save(booking);
    }

    public ServiceBooking updateServiceBookingStatus(UUID bookingId, UpdateBookingStatusRequest request, UUID parlourId) {
        ServiceBooking booking = serviceBookingRepository.findByIdAndParlourId(bookingId, parlourId)
                .orElseThrow(() -> new ResourceNotFoundException("Service booking not found"));

        ServiceBooking.BookingStatus status = ServiceBooking.BookingStatus.valueOf(request.getStatus().toUpperCase());
        booking.setStatus(status);
        
        if (status == ServiceBooking.BookingStatus.CANCELLED && request.getCancelReason() != null) {
            booking.setCancelReason(request.getCancelReason());
        }

        return serviceBookingRepository.save(booking);
    }
}
