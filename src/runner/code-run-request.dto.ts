import { ApiProperty } from '@nestjs/swagger';

export class CodeRunRequestDto {
  @ApiProperty({
    example: 'javascript',
    description: '프로그래밍 언어',
    required: true,
  })
  language: string;

  @ApiProperty({
    example: "console.log('Hello World');",
    description: '실행할 코드',
    required: true,
  })
  code: string;

  @ApiProperty({
    example: '1 2',
    description: '입력값',
    required: true,
  })
  input: string;

  @ApiProperty({
    example: 'Hello world!',
    description: '정답',
    required: true,
  })
  answer: string;

  @ApiProperty({
    example: 1,
    description: '시간 제한(초)',
    required: true,
    type: Number,
  })
  timeLimitSecond: number;
}
