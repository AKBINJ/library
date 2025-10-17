package com.example.library.service.impl;

import com.example.library.common.enums.Status;
import com.example.library.dto.BorrwDto.Request.BorrowCreateRequestDto;
import com.example.library.dto.BorrwDto.Response.BorrowResponseDto;
import com.example.library.dto.ResponseDto;
import com.example.library.entity.Book;
import com.example.library.entity.BorrowLog;
import com.example.library.entity.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowLogRepository;
import com.example.library.repository.MemberRepository;
import com.example.library.service.BorrowService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowServiceImpl implements BorrowService {
    private final BorrowLogRepository borrowLogRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BorrowResponseDto borrow(Long memberId, Long bookId, BorrowCreateRequestDto dto) {
//        BorrowResponseDto data = null;
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("멤버 아이디를 찾을 수 없습니다."));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("책 아이디를 찾을 수 없습니다."));

        if (member.getStatus() == Status.ACTIVE) {
        }

//        BorrowLog saved = BorrowLogRepository.save(borrowLog);


//        return ResponseDto.setSuccess("대출 성공", saved);
        return null;
    }
}
