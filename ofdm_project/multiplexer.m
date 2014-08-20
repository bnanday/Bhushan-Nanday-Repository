function [ pilot ] = multiplexer( random_bits,pilot )


rb_counter=1;

for m=1:140
    for n=1:300
        if(pilot(n,m)==0+0i)
            pilot(n,m)=random_bits(1,rb_counter);
            rb_counter = rb_counter+1;
        end
    end
end








% pilot_counter=1;
% symbol_counter =1;
% total_counter=1;
% freq_counter=1;
% insert_counter=6;
% symbol=1;
% 
% while(total_counter<=42000)
%     
%     if (symbol ==1)
%         if(freq_counter==1)
%             insert_counter=1;
%         end
%         insert_counter=insert_counter-1;
%         if (insert_counter==0)
%             if (insert_counter>2000)
%                 insert_counter=2000;
%             end
%             multiplexed_data(total_counter)=pilot_bits(pilot_counter);
%             pilot_counter=pilot_counter+1;
%             total_counter=total_counter+1;
%             insert_counter=6;
%         else
%             multiplexed_data(total_counter) = random_bits(symbol_counter);
%             symbol_counter=symbol_counter+1;
%             total_counter=total_counter+1;
%         end
%     end
%     
%     if (symbol ==5)
%         if(freq_counter==1)
%             insert_counter=100
%         end
%         if(freq_counter==4)
%             insert_counter=1;
%         end
%         insert_counter=insert_counter-1;
%         if (insert_counter==0)
%             multiplexed_data(total_counter)=pilot_bits(pilot_counter);
%             pilot_counter=pilot_counter+1;
%             total_counter=total_counter+1;
%             insert_counter=6;
%         else
%             multiplexed_data(total_counter) = random_bits(symbol_counter);
%             symbol_counter=symbol_counter+1;
%             total_counter=total_counter+1;
%         end
%     end
%     
%     if(symbol ~= 1 && symbol ~= 5)
%         
%        multiplexed_data(total_counter) = random_bits(symbol_counter);
%        symbol_counter=symbol_counter+1;
%        total_counter=total_counter+1;
%     end
%             
%      if ( mod(total_counter,300)==0)
%          symbol=symbol+1;
%      end
%      
%      if (symbol>7)
%          symbol=1;
%      end
%      
%      freq_counter=freq_counter+1;
%      
%      if(freq_counter>300)
%          freq_counter=1;
%      end
% end





end

