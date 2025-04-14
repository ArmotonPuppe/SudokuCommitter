import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuSolutionTest {

    @Test
    public void testPuzzleMatches(){
        String puzzle = "005007001010052700370000006654000300001090000007300100083760200026100000009020800";
        String solution = "965837421418652739372914586654271398831495672297386145583769214726148953149523867";

        for(int i = 0; i < puzzle.length(); i++){
            if(puzzle.charAt(i) != '0'){
                assertEquals(solution.charAt(i), puzzle.charAt(i), "didn't match at: " + i);
            }
        }
    }
    @Test
    public void testPuzzleMatches2(){
        String string1 = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDjdKOisKEBBn0Kp02afIX538AARNmXdB7dE/v+FFCPns6ElswI6PuCbmLgw1qyHBu+GnoQmn6gitULyP/tSerPssc9inYJynWXq4R9Zjob10tW/6SWNFCRrC6uzW8uUFEvvNL2yAdXEwLjmz7Ww6YVMFp+z+9JEnROy6IP9+Vinl0LBqz32HGbTGiEQxojkEmykd3adM6TVEVb+ahHuw9QQr4hOwwddxEJkxjvyStATcK3B28DeUjMBUQeFpBeo0kim1BdLlh14ub/aMlC1B2cdwsv9WJMKm84IxAGAG6SOujgU1jkeAYZhqMKpDvQqvWFB9rGACxFjhUjMern4UXtNzuyw8LZzVISR1HoYP0yVez1BE0g6aPwPEBtiXlljEcHnDRSau1tUPWx6KTCis0cgcj90XsXH+Pajwjwy9ErzB9mXTmtQPBX47QT4FAilqLc9m01cYKsYUHDEW4kWr/614JZaYNgcZocLwxOLNns9J2Xn1fyzrKarXAUP3cZNrIgR9umrhGiPtym0CFQJxItJdFBDOHVM1+smNeYFkiJEc6bIpUYIGxDt8kMvF3TqCc1Hsj0EJzy2isKgdELvbslbggbRQ9r7+3jBK0JwmUuEoIyp0aV5osj0cMZSAsmTrP++inrhgGAYqDlc5tgIwF7A0iauw2PtIDJA2UwZL2Uxw";
        String string2 = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDjdKOisKEBBn0Kp02afIX538AARNmXdB7dE/v+FFCPns6ElswI6PuCbmLgw1qyHBu+GnoQmn6gitULyP/tSerPssc9inYJynWXq4R9Zjob10tW/6SWNFCRrC6uzW8uUFEvvNL2yAdXEwLjmz7Ww6YVMFp+z+9JEnROy6IP9+Vinl0LBqz32HGbTGiEQxojkEmykd3adM6TVEVb+ahHuw9QQr4hOwwddxEJkxjvyStATcK3B28DeUjMBUQeFpBeo0kim1BdLlh14ub/aMlC1B2cdwsv9WJMKm84IxAGAG6SOujgU1jkeAYZhqMKpDvQqvWFB9rGACxFjhUjMern4UXtNzuyw8LZzVISR1HoYP0yVez1BE0g6aPwPEBtiXlljEcHnDRSau1tUPWx6KTCis0cgcj90XsXH+Pajwjwy9ErzB9mXTmtQPBX47QT4FAilqLc9m01cYKsYUHDEW4kWr/614JZaYNgcZocLwxOLNns9J2Xn1fyzrKarXAUP3cZNrIgR9umrhGiPtym0CFQJxItJdFBDOHVM1+smNeYFkiJEc6bIpUYIGxDt8kMvF3TqCc1Hsj0EJzy2isKgdELvbslbggbRQ9r7+3jBK0JwmUuEoIyp0aV5osj0cMZSAsmTrP++inrhgGAYqDlc5tgIwF7A0iauw2PtIDJA2UwZL2Uxw";
        assertEquals(string1, string2);
    }
}
